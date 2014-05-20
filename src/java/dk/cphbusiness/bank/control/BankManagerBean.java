/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.cphbusiness.bank.control;

import dk.cphbusiness.bank.contract.BankManager;
import dk.cphbusiness.bank.contract.dto.AccountDetail;
import dk.cphbusiness.bank.contract.dto.AccountIdentifier;
import dk.cphbusiness.bank.contract.dto.AccountSummary;
import dk.cphbusiness.bank.contract.dto.CheckingAccountDetail;
import dk.cphbusiness.bank.contract.dto.CustomerDetail;
import dk.cphbusiness.bank.contract.dto.CustomerIdentifier;
import dk.cphbusiness.bank.contract.dto.CustomerSummary;
import dk.cphbusiness.bank.contract.eto.CustomerBannedException;
import dk.cphbusiness.bank.contract.eto.InsufficientFundsException;
import dk.cphbusiness.bank.contract.eto.NoSuchAccountException;
import dk.cphbusiness.bank.contract.eto.NoSuchCustomerException;
import dk.cphbusiness.bank.contract.eto.TransferNotAcceptedException;
import static dk.cphbusiness.bank.control.Assembler.createAccountDetail;
import static dk.cphbusiness.bank.control.Assembler.createAccountSummaries;
import static dk.cphbusiness.bank.control.Assembler.createCheckingAccountEntity;
import static dk.cphbusiness.bank.control.Assembler.createCustomerSummaries;
import static dk.cphbusiness.bank.control.Assembler.createCustomerDetail;
import dk.cphbusiness.bank.model.Account;
import dk.cphbusiness.bank.model.CheckingAccount;
import dk.cphbusiness.bank.model.Person;
import dk.cphbusiness.bank.model.Postal;
import dk.cphbusiness.bank.model.Transfer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Mykremin
 */
@Stateless
public class BankManagerBean implements BankManager {
    @PersistenceContext(unitName = "BankBackend_MRJPU")
    private EntityManager em;
    
    
//    @Override
//    public String sayHello(String name) { // dette er en test for at se om man har hul til ens backend
//        return "Hello " +name+ " from bank manager bean";
//    }
    
    @Override
    public Collection<CustomerSummary> listCustomers() {
        Query query = em.createNamedQuery("Person.findAll");
        Collection<Person> persons = query.getResultList();
        return createCustomerSummaries(persons); // disse metoder bliver kaldt inde fra assembleren
    }

    @Override
    public Collection<AccountSummary> listAccounts() {
        Query query = em.createNamedQuery("Account.findAll");
        Collection<Account> accounts = query.getResultList();
        return createAccountSummaries(accounts);
    }

    @Override
    public Collection<AccountSummary> listCustomerAccounts(CustomerIdentifier customerID) {
        Person customer = em.find(Person.class, customerID.getCpr());
        Collection<Account> accounts = customer.getAccounts();
        return createAccountSummaries(accounts);
    }

    @Override
    public AccountDetail transferAmount(BigDecimal amount, AccountIdentifier source, AccountIdentifier target) throws NoSuchAccountException, TransferNotAcceptedException, InsufficientFundsException {
      Account sourceAccount = em.find(Account.class, source.getNumber());
      Account targetAccount = em.find(Account.class, target.getNumber());
      sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
      targetAccount.setBalance(targetAccount.getBalance().add(amount));
      Transfer transfer = new Transfer(null, amount.negate(), sourceAccount, targetAccount);
      transfer.setDate(new Date());
      em.persist(transfer);
      return createAccountDetail(sourceAccount);
    }

    @Override
    public AccountDetail showAccountHistory(AccountIdentifier accountIdentifier) {   
        Account account = em.find(Account.class, accountIdentifier.getNumber());
        em.refresh(account);
        return createAccountDetail(account);
    }

    @Override
    public CustomerDetail saveCustomer(CustomerDetail customer) {
        Postal code = em.find(Postal.class, customer.getPostalCode());
        if (code == null) {
            code = new Postal(customer.getPostalCode(), customer.getPostalDistrict());
            em.persist(code);
        }
        Person person = new Person(
                customer.getCpr(), 
                customer.getTitle(), 
                customer.getFirstName(), 
                customer.getLastName(), 
                customer.getStreet(), 
                code, 
                customer.getPhone(), 
                customer.getEmail(), 
                customer.getPassword());
        
        if (em.find(Person.class, customer.getCpr()) == null) {
            
            em.persist(person);
        } else{
            
            person = em.merge(person);
        }
        return createCustomerDetail(person);

    }

    @Override
    public CustomerDetail showCustomer(CustomerIdentifier customerID) throws NoSuchCustomerException {
        Person customer = em.find(Person.class, customerID.getCpr());
        if (customer == null) {
            throw new NoSuchCustomerException(customerID);
        }
        return createCustomerDetail(customer);

            }

    @Override
    public AccountDetail createAccount(CustomerIdentifier customerID, AccountDetail detail) throws NoSuchCustomerException, CustomerBannedException {
        //Account account = new Account();
        Person customer = em.find(Person.class, customerID.getCpr());
        
        if(customer == null){
            throw new NoSuchCustomerException(customerID);
        }
        if(detail instanceof CheckingAccountDetail){
            CheckingAccount checkA = createCheckingAccountEntity((CheckingAccountDetail) detail);
            em.persist(checkA);
            customer.getAccounts().add(checkA);
            em.persist(customer);
            return createAccountDetail(checkA);
        }
        throw new RuntimeException("Account type is unknown");
    }


    @Override
    public Collection<String> listAccountTypes() {
        Collection<String> accountTypes = new ArrayList<>();
        accountTypes.add("Checking Account");
        accountTypes.add("Time Deposit Account");
        accountTypes.add("Money Market Account");
        return accountTypes;
    }
    
    @Override
    public boolean checkCustomerCpr(String cpr) {
      Person person = em.find(Person.class, cpr);
      
      if(person == null){         
          return true;
      } else{         
          return false;
      }

    }
    
        @Override
    public Collection<String> listCustomerEmails() {
      Collection<String> emails = new ArrayList<String>();  
      Collection<CustomerSummary> customers = listCustomers();
      
            for (CustomerSummary customer : customers) {
                emails.add(customer.getEmail());
            }
            return emails;

    }

    public void persist(Object object) {
        em.persist(object);
    }
  
}
