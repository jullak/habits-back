package com.jullak.habits.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jullak.habits.model.User;
import com.jullak.habits.repository.UserRepository;
import org.hibernate.service.spi.OptionallyManageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/registration", method=RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> registrateUser(@RequestBody Map<String, Object> body) {
        JSONObject registerResult = new JSONObject();

        try {
            Optional<User> isExist = userRepository.findByNickname((String) body.get("nickname"));
            if (isExist.isPresent()) {
                registerResult.put("registerResult", "exists");
            } else {
                userRepository.save(new User((String) body.get("nickname"), (String) body.get("password")));
                registerResult.put("registerResult", "success");
            }
        } catch (Exception e) {
            registerResult.put("registerResult", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(registerResult.toString());
        }
        return ResponseEntity.ok().body(registerResult.toString());
    }

    @RequestMapping(value = "/login", method=RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, Object> body) {
        JSONObject result = new JSONObject();

        try {
            Optional<User> getUser = userRepository.findByNicknameAndPassword((String) body.get("nickname"), (String) body.get("password"));
            if (getUser.isPresent()) {
                //some session logic
                result.put("loginResult", "success");
            } else {
                result.put("loginResult", "not found");
            }
        } catch (Exception e) {
            result.put("loginResult", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.toString());
        }
        return ResponseEntity.ok().body(result.toString());
    }
}
