package main;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import repository.UserRepository;

import java.sql.Timestamp;

@Component
public class UserCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = new User("John Doe", new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
        User foundUser = userRepository.findById(user.getId()).orElse(null);
        System.out.println("Found User: " + (foundUser != null ? foundUser.getName() : "Not found"));
        if (foundUser != null) {
            foundUser.setName("Jane Doe");
            userRepository.save(foundUser);
        }
        userRepository.delete(foundUser);
    }
}
