package com.example.training.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;

    @Size(min=2, message = "Customer name must be greater than 2 character")
    private String customerName;


    private Double totalPrice;


    private String invoiceDate;

    private Integer invoiceNumber;

    @Column(nullable = true, length = 64)
    private String InvoicePic;

    @ManyToOne()
    @JoinColumn(name="user_id")
    private User owner ;

    @JsonManagedReference
    @OneToMany(mappedBy = "relatedInvoice" ,cascade = CascadeType.ALL)
    private List<Items> invoiceItems;


    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;


    public Invoice() {
    }

    public List<Items> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<Items> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoicePic() {
        return InvoicePic;
    }

    public void setInvoicePic(String invoicePic) {
        InvoicePic = invoicePic;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}
