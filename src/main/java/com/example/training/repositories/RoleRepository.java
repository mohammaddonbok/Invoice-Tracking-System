package com.example.training.repositories;

import com.example.training.models.Role;
import com.example.training.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role,Long> {
    List<Role> findByName(String name);
    List<Role> findAll();
    Role findRoleByUsers(User user);
}
