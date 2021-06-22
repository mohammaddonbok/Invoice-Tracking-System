package com.example.training.repositories;

import com.example.training.models.Invoice;
import com.example.training.models.Items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsRepository extends JpaRepository<Items,Long> {

}
