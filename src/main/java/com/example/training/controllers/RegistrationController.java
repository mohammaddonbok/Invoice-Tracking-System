package com.example.training.controllers;

import com.example.training.exception.ApiRequestException;
import com.example.training.models.Invoice;
import com.example.training.models.Role;
import com.example.training.models.User;
import com.example.training.repositories.RoleRepository;
import com.example.training.repositories.UserRepository;
import com.example.training.service.Services;
import com.example.training.service.UserDetails;
import com.example.training.token.JwtResponse;
import com.example.training.token.TokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
public class RegistrationController {
    private final Services service;
    private final TokenUtil tokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetails userDetails;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    public RegistrationController(Services service, TokenUtil tokenUtil, AuthenticationManager authenticationManager, UserDetails userDetails, RoleRepository roleRepository, UserRepository userRepository) {
        this.service = service;
        this.tokenUtil = tokenUtil;
        this.authenticationManager = authenticationManager;
        this.userDetails = userDetails;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/api/createUser")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {

        return service.saveWithUserRole(user);
    }
//    @CrossOrigin(origins = "*", allowedHeaders = "*")
//        @GetMapping(value = "/api/register")
//        public String showSignUp(){
//            return null;


@PostMapping(value =  "/logIn")
    public JwtResponse Login(@Valid @RequestBody User user) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        org.springframework.security.core.userdetails.UserDetails userDetails1 = userDetails.loadUserByUsername(user.getUsername());


        if((!(service.getUser(user.getUsername()).getActivated()))){
        throw new ApiRequestException("Your account have been deactivated");
    }
        String token = tokenUtil.generateToken(userDetails1);
        return new JwtResponse(token);

    }
    @GetMapping(value = "/api/displayUsers")
    public List<User> showAll(@RequestHeader(value = "Authorization" ,required = false) String header) {
        String token = header.substring("Bearer ".length());
        String email = tokenUtil.getUserNameFromToken(token);
        User user = service.fetchUser(email);
        Role role = roleRepository.findRoleByUsers(user);
        List<User> allUsers = service.findAll(role);
        return allUsers;
    }

    // TODO: 6/23/2021 : from delete to put and add request body;
    @PostMapping("/api/user/{id}")
    @ResponseBody
    public ResponseEntity<Invoice> userStatus(@PathVariable("id") String id , @RequestBody String status ){

        service.changeStatus(Long.parseLong(id) , Boolean.parseBoolean(status) );
        return ResponseEntity.noContent().build();
    }
}
