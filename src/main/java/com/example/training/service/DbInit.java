//package com.example.training.service;
//
//import com.example.training.models.User;
//import com.example.training.repositories.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.List;
//@Service
//public class DbInit implements CommandLineRunner {
//
//    private UserRepository userRepository;
//    private PasswordEncoder passwordEncoder;
//    public DbInit(UserRepository userRepository , PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
////        User mohammad = new User("user@g.com", passwordEncoder.encode("user"), "ACCESS_SUPPORT,ACCESS_SUPPORT_SUPER");
////        User admin = new User("admin@g.com", passwordEncoder.encode("admin"), "ACCESS_ALL");
////        User auditor = new User("auditor@g.com" , passwordEncoder.encode( "auditor") , "ACCESS_SUPER_AUDITOR");
////
////        List<User> users = Arrays.asList(mohammad , admin , auditor);
////
////        this.userRepository.saveAll(users);
//
//    }
//
//}
