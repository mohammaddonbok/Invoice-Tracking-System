package com.example.training.controllers;

import com.example.training.config.AuthFilter;
import com.example.training.models.FileUploadUtils;
import com.example.training.models.Invoice;
import com.example.training.models.User;
import com.example.training.repositories.InvoiceRepository;
import com.example.training.service.Services;
import com.example.training.token.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")

@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class InvoicesController {
    private final Services invoiceService;
    private final InvoiceRepository invoiceRepository;
    private final TokenUtil tokenUtil;
    private final AuthFilter auth;

    public InvoicesController(Services invoiceService, InvoiceRepository invoiceRepository, TokenUtil tokenUtil, AuthFilter auth) {
        this.invoiceService = invoiceService;
        this.invoiceRepository = invoiceRepository;
        this.tokenUtil = tokenUtil;
        this.auth = auth;
    }

    @GetMapping("/invoices")
    //toDo: do pagination and search and sorting
    public List<Invoice> getAllInvoice() {
        return invoiceService.returnAllInvoice();
    }


    @GetMapping("/OwnerInvoices")
    public List<Invoice> getUserInvoice(@RequestHeader("Authorization") String header){
        String token = header.substring("Bearer ".length());
        System.out.println(token);
        String email = tokenUtil.getUserNameFromToken(token);
        User user =   invoiceService.fetchUser(email);
        return invoiceService.getOwnerInvoicesFromToken(user);
    }

    @PostMapping("/createInvoice")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice ,
                                                 @RequestParam("email")String mail,
                                                 @RequestHeader("Authorization") String header) {
        System.out.println(header);
            User Supportuser = invoiceService.fetchUser(mail);
            invoiceService.createInvoice(invoice , Supportuser);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/invoice/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id,
                                                 @RequestBody Invoice invoiceDetails,
                                                 @RequestParam("email")String mail,
                                                 @RequestHeader("Authorization")String header
                                                 ) {

        String token = header.substring("Bearer ".length());
        System.out.println(token);
        System.out.println(mail);
        User Supportuser = invoiceService.fetchUser(mail);
        System.out.println(Supportuser);
        invoiceService.updateInvoice(id, invoiceDetails , Supportuser);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/invoice/{id}")
    public Invoice getOneInvoice(@PathVariable Long id) {

        return invoiceService.getInvoiceById(id);
    }


    @DeleteMapping("/invoice/{id}")
    @ResponseBody
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable Long id){
        invoiceService.deleteSpecificInvoice(id);
        return ResponseEntity.noContent().build();
    }
}
