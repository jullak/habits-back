package com.jullak.habits.controller;

import com.jullak.habits.model.User;
import com.jullak.habits.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/registration", method= RequestMethod.POST)
    public String registrateUser(@RequestBody String nickname, @RequestBody String password) {
        Optional<User> isExist = userRepository.findByNickname(nickname);
        if (isExist.isPresent()) {
            return "AlreadyExist";
        }
        userRepository.save(new User(nickname, password));
        return "I do something)";
    }

}
