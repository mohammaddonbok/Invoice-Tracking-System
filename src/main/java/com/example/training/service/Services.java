package com.example.training.service;


import com.example.training.models.User;
import com.example.training.repositories.InvoiceRepository;
import com.example.training.repositories.RoleRepository;
import com.example.training.repositories.UserRepository;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class Services {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final InvoiceRepository invoiceRepository;



    public Services(UserRepository userRepository, RoleRepository roleRepository, InvoiceRepository invoiceRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.invoiceRepository = invoiceRepository;
    }
   
    public User saveWithUserRole(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userRepository.save(user);
    }

    // 2
    public User saveUserWithAuditorRole(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        user.setRoles(roleRepository.findByName("Auditor"));
        return userRepository.save(user);
    }

    // 3
    public Optional<User> findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

}
