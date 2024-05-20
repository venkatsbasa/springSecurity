package com.spring.security.controller;

import com.spring.security.model.MyUser;
import com.spring.security.model.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
public class RegisterController {
    @Autowired
    private MyUserRepo myUserRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register/user")
    public ResponseEntity<MyUser> registerUser(@RequestBody MyUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        MyUser save = myUserRepo.save(user);
        return  new ResponseEntity<MyUser>(save,HttpStatus.CREATED);
    }
}
