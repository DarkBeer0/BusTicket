package controller;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        user.ifPresent(userService::deleteUser);
    }
}
