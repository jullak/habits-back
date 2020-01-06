package com.jullak.habits.controller;

import com.jullak.habits.model.User;
import com.jullak.habits.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/registration", method=RequestMethod.POST, produces = "application/json")
    public String registrateUser(@RequestParam String nickname, String password) {
        Optional<User> isExist = userRepository.findByNickname(nickname);
        if (isExist.isPresent()) {
            return "AlreadyExist";
        }
        userRepository.save(new User(nickname, password));
        return "I do something)";
    }

}
