package com.example.transportapi.service;

import com.example.transportapi.model.Permission;
import com.example.transportapi.model.Transport;
import com.example.transportapi.model.User;
import com.google.gson.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransportService {
    public static List<Transport> transports = new ArrayList<>();
    private static final String USER_API_URL = "http://localhost:8080/users";

    private static int lastId = 1;

    static {
        transports.add(new Transport("sand", 10, "Munich", "Berlin", LocalDate.of(2023, 3, 23)));
        transports.add(new Transport("sand", 20, "Budapest", "Berlin", LocalDate.of(2023, 3, 25)));
    }

    public static int getLastId() {
        return lastId;
    }

    public static void incrementLastId() {
        lastId++;
    }

    public static HttpResponse<String> getUser(String userName) {
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(USER_API_URL + "?user=" + userName))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            return httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        }catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static Transport checkTransportData(Transport transport) {
        Optional<Transport> foundTransportOptional = transports.stream().filter(t -> t.getId() == transport.getId()).findFirst();
        if(foundTransportOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This transport data has not been found with this id");
        }
        return foundTransportOptional.get();

    }

    public static List<Transport> getTransportData(String userName) {
        checkUser(userName);
        return transports;
    }

    public static Optional<Transport> getTransportData(String userName, int id) {
        checkUser(userName);
        return transports.stream().filter(user -> user.getId() == id).findFirst();
    }

    public static Transport addTransportData(String userName, Transport transport) {
        checkUser(userName);
        transport.setId(TransportService.getLastId());
        transports.add(transport);
        return transport;
    }

    public static Transport editTransportData(String userName, Transport transport) {
        checkUser(userName);
        Transport foundTransport = checkTransportData(transport);
        foundTransport.setFreight(transport.getFreight());
        foundTransport.setWeight(transport.getWeight());
        foundTransport.setFromPlace(transport.getFromPlace());
        foundTransport.setToPlace(transport.getToPlace());
        foundTransport.setDueDate(transport.getDueDate());
        return transport;
    }

    public static Optional<Transport> removeTransportData(String userName, int id) {
        Optional<Transport> foundTransport = getTransportData(userName, id);
        foundTransport.ifPresent(transport -> transports.remove(transport));

        return foundTransport;
    }

    public static boolean userCanAccess(User user) {
        if(user.isLocked()) {
            return false;
        }
        for(Permission permission : user.getPermissions()) {
            if(permission.equals(Permission.ADMIN)) {
                return true;
            }
        }
        return false;
    }

    public static void checkUser(String userName) {
        HttpResponse<String> httpResponse = getUser(userName);
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_DATE_TIME); }

        }).create();
        User user = null;
        if (httpResponse != null) {
            user = gson.fromJson(httpResponse.body(), User.class);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user doesn't exist");
            }
            if (!userCanAccess(user)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "This user is not authorized to access transport data");
            }

        }

    }

}
