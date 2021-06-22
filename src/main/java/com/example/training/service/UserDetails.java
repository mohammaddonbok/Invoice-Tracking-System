package com.example.training.service;

import com.example.training.exception.ApiRequestException;
import com.example.training.exception.NotFoundException;
import com.example.training.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails( UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new User("Moh",passwordEncoder().encode("password"), AuthorityU);

        org.springframework.security.core.userdetails.UserDetails user = userRepository.findByEmail(username);
        if(user == null ){
            throw new ApiRequestException("The username or password you have entered is invalid");

        }
        return user;
    }



}
