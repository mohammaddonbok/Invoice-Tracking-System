package com.example.training.controllers;

import com.example.training.models.User;
import com.example.training.service.Services;

import com.example.training.validator.Validations;
//import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
@RestController
@RequestMapping("/api")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class RegistrationController {
    private final Services service;
    private final Validations validator;

    public RegistrationController(Services service, Validations validator) {
        this.service = service;
        this.validator = validator;
    }

    @PostMapping(value = "/createUser")
    public User register(@RequestBody User user , HttpSession session) {
        return service.saveWithUserRole(user);
    }
//    @CrossOrigin(origins = "*", allowedHeaders = "*")
//        @GetMapping(value = "/api/register")
//        public String showSignUp(){
//            return null;

        @GetMapping(value = "/displayUsers")
        public List<User> showAll(){
            List<User> allUsers = service.findAll();
            return allUsers;
        }

//    @RequestMapping ("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        return "";
//    }

}
