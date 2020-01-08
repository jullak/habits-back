package com.jullak.habits.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jullak.habits.model.User;
import com.jullak.habits.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/registration", produces = "application/json")
    public ResponseEntity<String> registrateUser(@RequestParam String nickname, @RequestParam String password) {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();
        try {
            boolean isExist = userRepository.existsByNickname(nickname);
            if (isExist) {
                result.addProperty("registrationResult", "exists");
            } else {
                User user = userRepository.save(new User(nickname, password));
                result = gson.toJsonTree(user).getAsJsonObject();
                result.addProperty("registrationResult", "success");
            }
        } catch (Exception e) {
            result.addProperty("registrationResult", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.toString());
        }
        return ResponseEntity.ok().body(result.toString());
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<String> loginUser(@RequestParam String nickname, @RequestParam String password) {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        try {
            Optional<User> getUser = userRepository.findByNicknameAndPassword(nickname, password);
            if (getUser.isPresent()) {
                result = gson.toJsonTree(getUser.get()).getAsJsonObject();
                result.addProperty("loginResult", "success");
            } else {
                result.addProperty("loginResult", "not found");
            }
        } catch (Exception e) {
            result.addProperty("loginResult", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.toString());
        }
        return ResponseEntity.ok().body(result.toString());
    }
}
