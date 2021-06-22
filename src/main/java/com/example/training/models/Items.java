package com.example.training.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "items")
public class Items {
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;

    @Size(min = 3, message = "Item name must be greater than 3 characters")
    private String itemName;

    private Double pricePerOneItem;

    private Integer quantity;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "invoice_id" )
    private Invoice relatedInvoice;

    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

    public Items() {

    }

    public Invoice getRelatedInvoice() {
        return relatedInvoice;
    }

    public void setRelatedInvoice(Invoice relatedInvoice) {

        this.relatedInvoice = relatedInvoice;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Double getPricePerOneItem() {
        return pricePerOneItem;
    }

    public void setPricePerOneItem(Double pricePerOneItem) {
        this.pricePerOneItem = pricePerOneItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
