package com.example.training.controllers;

import com.example.training.models.Invoice;
import com.example.training.repositories.InvoiceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class InvoicesController {

    private final InvoiceRepository invoiceRepository;

    public InvoicesController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @GetMapping("/invoices")
    public List<Invoice> getAllInvoice(){
        return invoiceRepository.findAll();
    }

    @PostMapping("/createInvoice")
    public Invoice createInvoice(Invoice invoice){
        return invoiceRepository.save(invoice);
    }
}
