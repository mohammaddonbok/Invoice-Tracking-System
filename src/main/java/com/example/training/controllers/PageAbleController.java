package com.example.training.controllers;

import com.example.training.exception.ApiRequestException;
import com.example.training.models.Invoice;
import com.example.training.models.User;
import com.example.training.paging.InvoicePage;
import com.example.training.paging.InvoiceSearchCriteria;
import com.example.training.service.PagAbleService;
import com.example.training.service.Services;
import com.example.training.token.TokenUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PageAbleController {
    public final PagAbleService pagAbleService;
    private final Services invoiceService;
    private final TokenUtil tokenUtil;

    public PageAbleController(PagAbleService pagAbleService, Services invoiceService, TokenUtil tokenUtil) {
        this.pagAbleService = pagAbleService;
        this.invoiceService = invoiceService;
        this.tokenUtil = tokenUtil;
    }


    @GetMapping(value = "/pageAble")
    public ResponseEntity<Page<Invoice>> getInvoices(@RequestParam(value = "customerName", required = false) String name,
                                                     @RequestParam(value = "pageNum", required = false) String pageNumber,
                                                     InvoiceSearchCriteria invoiceSearchCriteria,
                                                     InvoicePage invoicePage) {

        User user = invoiceService.getUserFromToken();
        invoiceSearchCriteria.setOwner(user);
        if (name != null) {
            invoiceSearchCriteria.setCustomerName(name);
            return new ResponseEntity<>(pagAbleService.getInvoices(invoicePage, invoiceSearchCriteria),
                    HttpStatus.OK);
        }
        invoicePage.setPageNumber(Integer.parseInt(pageNumber));
        return new ResponseEntity<>(pagAbleService.getInvoices(invoicePage, invoiceSearchCriteria),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Invoice> addInvoice(@RequestBody Invoice invoice) {
        User username = invoiceService.getUserFromToken();
        if (!username.getRoles().get(0).getName().equals("Super_User") && !username.getRoles().get(0).getName().equals("Support_User")) {
            throw new ApiRequestException("Not Authorized");

        }
        return new ResponseEntity<>(pagAbleService.addInvoice(invoice), HttpStatus.OK);
    }
}
