/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.bank.control;

import dk.cphbusiness.bank.contract.dto.AccountDetail;
import dk.cphbusiness.bank.contract.dto.AccountSummary;
import dk.cphbusiness.bank.contract.dto.CheckingAccountDetail;
import dk.cphbusiness.bank.contract.dto.CustomerDetail;
import dk.cphbusiness.bank.contract.dto.CustomerSummary;
import dk.cphbusiness.bank.contract.dto.TransferSummary;
import dk.cphbusiness.bank.model.Account;
import dk.cphbusiness.bank.model.Person;
import dk.cphbusiness.bank.model.Postal;
import dk.cphbusiness.bank.model.Transfer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Mykremin
 */
public class Assembler {

    public static CustomerSummary createCustommerSummary(Person person) {
        return new CustomerSummary(
                person.getCpr(), 
                person.getFirstName() + " " + 
                        person.getLastName(), 
                person.getStreet() + ", " + 
                        person.getCode().getCode() + " " + 
                        person.getCode().getDistrict(), 
                person.getPhone(), 
                person.getEmail());
    }

    public static Collection<CustomerSummary> createCustomerSummaries(Collection<Person> persons) {
        Collection<CustomerSummary> summaries = new ArrayList<>();
        for (Person person : persons) {
            CustomerSummary summary = createCustommerSummary(person);
            summaries.add(summary);
        }
        return summaries;
    }
    
    

    public static AccountSummary createAccountSummary(Account account) {
        return new AccountSummary(account.getAccountNumber(), "Checking Account", new BigDecimal(account.getBalance()));
    }

    public static Collection<AccountSummary> createAccountSummaries(Collection<Account> accounts) {
        Collection<AccountSummary> summaries = new ArrayList<>();
        for (Account account : accounts) {
            AccountSummary summary = createAccountSummary(account);
            summaries.add(summary);
        }
        return summaries;
    }
    
    

    public static AccountDetail createAccountDetail(Account account) {
        List<Transfer> transfers = new ArrayList<>();
        transfers.addAll(account.getIncoming());
        transfers.addAll(account.getOutgoing());
        //Collections.sort(transfers);
        System.err.println("Transfers for #" + account.getAccountNumber() + " " + transfers.size());
        Collection<TransferSummary> transferSummaries = new ArrayList<>();
        for (Transfer transfer : transfers) {
            transferSummaries.add(createTransferSummary(account, transfer));
        }
        return new CheckingAccountDetail(account.getAccountNumber(), new BigDecimal(account.getInterest()), transferSummaries);

    }

    
    
    
    public static CustomerDetail createCustomerDetail(Person customer) {
        Postal postal = customer.getCode();
        return new CustomerDetail(
                customer.getCpr(),
                customer.getTitle(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStreet(),
                postal.getCode(),
                postal.getDistrict(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getPassword());

    }


    
    public static TransferSummary createTransferSummary(Account account, Transfer transfer) {
        if (transfer.getSource() == account) {
            return new TransferSummary(
                    transfer.getDate(),
                    new BigDecimal(transfer.getAmount()).negate(),
                    transfer.getTarget().getAccountNumber()
            );
        } else {
            return new TransferSummary(
                    transfer.getDate(),
                    new BigDecimal(transfer.getAmount()),
                    transfer.getSource().getAccountNumber()
            );
        }
    }

}
