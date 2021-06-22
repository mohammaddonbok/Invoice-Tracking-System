package com.example.training.service;

import com.example.training.models.Invoice;
import com.example.training.models.User;
import com.example.training.paging.InvoicePage;
import com.example.training.paging.InvoiceSearchCriteria;
import com.example.training.paging.InvoicesCriteriaRepository;
import com.example.training.repositories.InvoiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagAbleService {

    private final InvoiceRepository invoiceRepository;
    private final InvoicesCriteriaRepository invoicesCriteriaRepository;

    public PagAbleService(InvoiceRepository invoiceRepository, InvoicesCriteriaRepository invoicesCriteriaRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoicesCriteriaRepository = invoicesCriteriaRepository;
    }


    public Page<Invoice> getInvoices(InvoicePage invoicePage, InvoiceSearchCriteria invoiceSearchCriteria ){
        return invoicesCriteriaRepository.findAllWithFilters(invoicePage,invoiceSearchCriteria);


    }
    public Invoice addInvoice(Invoice invoice){
        return invoiceRepository.save(invoice);


    }
}
