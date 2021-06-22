package com.example.training.paging;

import com.example.training.models.Invoice;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class InvoicesCriteriaRepository {
    private final EntityManager entityManager;

    private final CriteriaBuilder criteriaBuilder;

    public InvoicesCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;

        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }
    public PageImpl findAllWithFilters(InvoicePage invoicePage,
                                       InvoiceSearchCriteria invoiceSearchCriteria){
        CriteriaQuery<Invoice> criteriaQuery = criteriaBuilder.createQuery(Invoice.class);
        Root<Invoice> invoiceRoot = criteriaQuery.from(Invoice.class);
        Predicate predicate = getPredicate(invoiceSearchCriteria , invoiceRoot);
        criteriaQuery.where(predicate);
        setOrder(invoicePage , criteriaQuery , invoiceRoot);

        TypedQuery<Invoice> typedQuery =entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(invoicePage.getPageNumber() * invoicePage.getPageSize());
        typedQuery.setMaxResults(invoicePage.getPageSize());

        Pageable pageable = getPageable(invoicePage);

        Long invoiceCount = getInvoiceCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable , invoiceCount);
    }


    private void setOrder(InvoicePage invoicePage, CriteriaQuery<Invoice> criteriaQuery, Root<Invoice> invoiceRoot) {
        if(invoicePage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(invoiceRoot.get(invoicePage.getSortBy())));
        }else{
            criteriaQuery.orderBy(criteriaBuilder.desc(invoiceRoot.get(invoicePage.getSortBy())));

        }
    }

    private Predicate getPredicate(InvoiceSearchCriteria invoiceSearchCriteria,
                                   Root<Invoice> invoiceRoot) {

        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(invoiceSearchCriteria.getCustomerName())) {
            predicates.add(
                    criteriaBuilder.like(invoiceRoot.get("customerName"),
                            "%" + invoiceSearchCriteria.getCustomerName() + "%")
            );
        }
        if(invoiceSearchCriteria.getOwner().getRoles().get(0).getName().equals("Super_User") || invoiceSearchCriteria.getOwner().getRoles().get(0).getName().equals("Auditor ")){
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }else
        if (Objects.nonNull(invoiceSearchCriteria.getOwner())) {
            predicates.add(
                    criteriaBuilder.equal(invoiceRoot.get("owner"),invoiceSearchCriteria.getOwner())
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }

        private Pageable getPageable(InvoicePage invoicePage) {
        Sort sort = Sort.by(invoicePage.getSortDirection() , invoicePage.getSortBy());

        return PageRequest.of(invoicePage.getPageNumber(), invoicePage.getPageSize() , sort);
    }
    private Long getInvoiceCount(Predicate predicate) {
    CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
    Root<Invoice> countRoot = countQuery.from(Invoice.class);
    countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
    return entityManager.createQuery(countQuery).getSingleResult();
    }

}
