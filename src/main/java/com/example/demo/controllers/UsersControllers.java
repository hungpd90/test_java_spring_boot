package com.example.demo.controllers;

import com.example.demo.utils.Bcrypt;
import com.example.demo.models.ResponseObject;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.validators.SignInValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/users")
public class UsersControllers {
    @Autowired
    private UserRepository repository;

    @PostMapping("/signup")
    ResponseEntity<ResponseObject> signUp (@RequestBody User newUser){
        try{
            Bcrypt bcrypt = new Bcrypt();
            newUser.setPassword(bcrypt.encoder().encode(newUser.getPassword()));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Sign up successfully", repository.save(newUser))
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", e.toString(), "")
            );
        }

    }

    @PostMapping("/login")
    ResponseEntity<ResponseObject> logIn (@RequestBody SignInValidator validator) {
        try {
            Bcrypt bcrypt = new Bcrypt();

            User user = repository.findByEmail(validator.getEmail());
            if (user == null) {
                throw new Exception("User with email not found!");
            }

            if (!bcrypt.encoder().matches(validator.getPassword(), user.getPassword())) {
                throw new Exception("Wrong password!");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("ok", "Sign in successfully", "")
            );


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", e.toString(), "")
            );
        }
    }

    @GetMapping("")
    ResponseEntity<ResponseObject> getAllUsers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Get users successfully", repository.findAll())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", e.toString(), "")
            );
        }
    }
}
