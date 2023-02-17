package com.example.userapi.service;
import com.example.userapi.model.Permission;
import com.example.userapi.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User("Peter", new Permission[]{Permission.ADMIN, Permission.USER}, LocalDateTime.now(), false));
        users.add(new User("John", new Permission[]{Permission.USER}, LocalDateTime.now(), false));
        users.add(new User("Kate", new Permission[]{Permission.USER}, LocalDateTime.now(), false));
        users.add(new User("James", new Permission[]{Permission.ADMIN, Permission.USER}, LocalDateTime.now(), true));
    }

    public List<User> getUsers() {
        return users;
    }

    public static Optional<User> getUserByName(String name) {
        return users.stream().filter(user -> user.getName().equalsIgnoreCase(name)).findFirst();
    }

    public static Optional<User> addUser(User user) {
        if(getUserByName(user.getName()).isEmpty()) {
            users.add(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    private static Integer getUserIndex(String userName) {
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getName().equalsIgnoreCase(userName)) {
                return i;
            }
        }
        return null;
    }

    public static Optional<User> deleteUser(String userName) {
        Optional<User> foundUser = users.stream().filter(user -> user.getName().equalsIgnoreCase(userName)).findFirst();
        if(foundUser.isPresent()) {
            users.remove(getUserIndex(userName));
        }
        return foundUser;

    }

    public static Optional<User> updateUser(User user) {
        if(getUserByName(user.getName()).isEmpty()) {
            return Optional.empty();
        }
        Integer userIndex = getUserIndex(user.getName());
        users.set(userIndex, user);
        return Optional.of(user);
    }

}
