package com.example.training.controllers;

import com.example.training.DTO.InvoiceDto;
import com.example.training.config.AuthFilter;
import com.example.training.exception.ApiRequestException;
import com.example.training.exception.MissingRequestHeaderException;
import com.example.training.models.FileUploadUtils;
import com.example.training.models.Invoice;
import com.example.training.models.User;
import com.example.training.repositories.InvoiceRepository;
import com.example.training.service.Services;
import com.example.training.token.TokenUtil;
import com.sun.mail.imap.protocol.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
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

        if (!invoiceService.getUserFromToken().getRoles().get(0).getName().equals("Super_User")) {
            throw new ApiRequestException("Not Authorized");
        }
        return invoiceService.returnAllInvoice();
    }


    @GetMapping("/OwnerInvoices")
    public List<Invoice> getUserInvoice() {

        User username = invoiceService.getUserFromToken();
        if (!username.getRoles().get(0).getName().equals("Super_User") && !username.getRoles().get(0).getName().equals("Support_User") && !username.getRoles().get(0).getName().equals("Auditor")) {
            throw new ApiRequestException("Not Authorized");
        }
        return invoiceService.getOwnerInvoicesFromToken(username);
    }

    @PostMapping("/createInvoice")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice,
                                                 @RequestParam("email") String mail
    ) {

        User username = invoiceService.getUserFromToken();
        if (!username.getRoles().get(0).getName().equals("Super_User") && !username.getRoles().get(0).getName().equals("Support_User")) {
            throw new ApiRequestException("Not Authorized");
        }
        User Supportuser = invoiceService.fetchUser(mail);
        invoiceService.createInvoice(invoice, Supportuser);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/invoice/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id,
                                                 @RequestBody Invoice invoiceDetails,
                                                 @RequestParam("email") String mail
    ) {

        User username = invoiceService.getUserFromToken();
        if (!username.getRoles().get(0).getName().equals("Super_User") && !username.getRoles().get(0).getName().equals("Support_User")) {
            throw new ApiRequestException("Not Authorized");
        }
        User Supportuser = invoiceService.fetchUser(mail);
        invoiceService.updateInvoice(id, invoiceDetails, Supportuser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/invoice/{id}")
    public Invoice getOneInvoice(@PathVariable Long id) {

        User username = invoiceService.getUserFromToken();
        if (!username.getRoles().get(0).getName().equals("Super_User") && !username.getRoles().get(0).getName().equals("Support_User") && !username.getRoles().get(0).getName().equals("Auditor") ) {
            throw new ApiRequestException("Not Authorized");
        }
        return invoiceService.getInvoiceById(id);
    }


    @DeleteMapping("/invoice/{id}")
    @ResponseBody
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable Long id) {
        User username = invoiceService.getUserFromToken();
        if (!username.getRoles().get(0).getName().equals("Super_User") && !username.getRoles().get(0).getName().equals("Support_User")) {
            throw new ApiRequestException("Not Authorized");
        }
                invoiceService.deleteSpecificInvoice(id);
                return ResponseEntity.noContent().build();
            }


    @GetMapping("/abstractInvoices")
    public List<Invoice> getAbstractInvoices() {
        if (!invoiceService.getUserFromToken().getRoles().get(0).getName().equals("Super_User")) {
            throw new ApiRequestException("Not Authorized");
        }
        return invoiceService.getAbstractInvoice();
    }
    @PostMapping("/attachInvoice")
    public ResponseEntity<Object> attachInvoices(
                                                 @RequestBody ArrayList<InvoiceDto> invoices,
                                                 @RequestParam("email") String mail) {
        User username = invoiceService.getUserFromToken();
        if (!username.getRoles().get(0).getName().equals("Super_User") && !username.getRoles().get(0).getName().equals("Support_User")) {
            throw new ApiRequestException("Not Authorized");
        }
            User user = invoiceService.fetchUser(mail);
            Invoice inv;
            for (int i = 0; i < invoices.size(); i++) {
                if (invoices.get(i).getActive()) {
                    inv = invoiceService.getInvoiceById(invoices.get(i).getId());
                    inv.setOwner(user);
                    invoiceRepository.save(inv);
                }
            }
            return new ResponseEntity<Object>(HttpStatus.CREATED);
        }

}
