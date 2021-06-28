package com.example.training.service;


import com.example.training.exception.ApiRequestException;
import com.example.training.exception.NotFoundException;
import com.example.training.models.Invoice;
import com.example.training.models.Role;
import com.example.training.models.User;
import com.example.training.repositories.InvoiceRepository;
import com.example.training.repositories.ItemsRepository;
import com.example.training.repositories.RoleRepository;
import com.example.training.repositories.UserRepository;
import com.example.training.token.TokenUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Services {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final InvoiceRepository invoiceRepository;
    private final ItemsRepository itemRepository;


    // toDo: why Autowired annotation and defference
    public Services(UserRepository userRepository, TokenUtil tokenUtil, ItemsRepository itemRepository, RoleRepository roleRepository, InvoiceRepository invoiceRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.invoiceRepository = invoiceRepository;
        this.itemRepository = itemRepository;

    }
    //toDo: DTO creation

    public ResponseEntity<User> saveWithUserRole(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ApiRequestException("Email already Exist");
        } else {
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

            userRepository.save(user);
            return new ResponseEntity<User>(HttpStatus.CREATED);
        }
    }

    public User fetchUser(String email) {
        return userRepository.findByEmail(email);
    }

    public void createInvoice(Invoice invoice, User user) {
        invoice.setOwner(user);
        invoiceRepository.save(invoice);
//        Invoice newInvoice =
//        List<Items> invoiceItem = newInvoice.getInvoiceItems();
//        for(int i = 0; i < invoiceItem.size(); i++) {
//            Items it = invoiceItem.get(i);
//            it.setRelatedInvoice(newInvoice);
//            itemRepository.save(it);
//        }
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Invoice> getOwnerInvoicesFromToken(User user) {

        return invoiceRepository.findByOwner(user);
    }

    public void changeStatus(Long id, boolean status ) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User changedUser = user.get();
            List<Invoice> userInvoices = invoiceRepository.findByOwner(changedUser);
            for (int i = 0 ; i< userInvoices.size() ; i++){
                userInvoices.get(i).setOwner(null);
            }
            changedUser.setActivated(status);
            userRepository.save(changedUser);
        } else {
            throw new NotFoundException("Error while fetching user no user found");
        }
    }

    public void deleteSpecificInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    public ResponseEntity<Invoice> updateInvoice(Long id, Invoice details, User owner) {
        System.out.println(owner);
        Optional<Invoice> modifyInvoice = invoiceRepository.findById(id);
        if (modifyInvoice.isPresent()) {
            Invoice updatedInvoice = modifyInvoice.get();
            updatedInvoice.setCustomerName(details.getCustomerName());
            updatedInvoice.setTotalPrice(details.getTotalPrice());
            updatedInvoice.setInvoiceDate(details.getInvoiceDate());
            updatedInvoice.setOwner(owner);
            updatedInvoice.setInvoiceNumber(details.getInvoiceNumber());
            updatedInvoice.setInvoiceItems(details.getInvoiceItems());
            invoiceRepository.save(updatedInvoice);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //toDo: cascade types and lazy , EAGER fetching
    //toDo: save invoice and save items OR don't save anything
    //toDo: if invoice not found reflect this on the API Response
    public List<Invoice> returnAllInvoice() {
        return invoiceRepository.findAll();

    }

    public Invoice getInvoiceById(Long id) {
        if (invoiceRepository.findById(id).isPresent()) {
            return invoiceRepository.findById(id).get();
        } else {
            throw new NotFoundException("Invoice not Found");
        }
    }

    public List<User> findAll(Role role) {
//        return userRepository.findUsersByRolesIsNotLikeAndActivatedTrue(role);
        return userRepository.findUsersByRolesIsNotLike(role);
    }
    public List<Invoice> getAbstractInvoice(){
        if((invoiceRepository.findInvoicesByOwnerIsNull()).isEmpty()){
            return null;
        }
        else {
            return invoiceRepository.findInvoicesByOwnerIsNull();
        }
    }

}
