package com.example.training.repositories;

import com.example.training.models.Invoice;
import com.example.training.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    List<Invoice> findAll();
    List<Invoice> findByOwner(User user );
    int countByOwner(User user);
    List<Invoice> findInvoicesByOwnerIsNull();

}
