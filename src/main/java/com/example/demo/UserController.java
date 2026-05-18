package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private List<User> users = new ArrayList<>();

    public UserController() {
        users.add(new User(1, "Alice", "alice@example.com"));
        users.add(new User(2, "Bob", "bob@example.com"));
        users.add(new User(3, "Carol", "carol@example.com"));
    }

    @GetMapping
    public List<User> getUsers() {
        return users;
    }
}