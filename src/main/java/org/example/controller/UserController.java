package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // REGISTER
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public User getUser(@PathVariable ObjectId id) {
        return userService.getUserById(id);
    }

    // GET USER BY EMAIL
    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
}