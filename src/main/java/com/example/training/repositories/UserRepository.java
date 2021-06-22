package com.example.training.repositories;

import com.example.training.models.Role;
import com.example.training.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    User findByEmail(String email);

    List<User> findUsersByRolesIsNotLikeAndActivatedTrue(Role role);
    List<User> findUsersByRolesIsNotLike(Role role);

}
