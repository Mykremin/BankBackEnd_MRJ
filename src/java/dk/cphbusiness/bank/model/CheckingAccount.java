/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.cphbusiness.bank.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Mykremin
 */
@Entity
@Table(name = "CHECKINGACCOUNT")
@NamedQueries({
    @NamedQuery(name = "CheckingAccount.findAll", query = "SELECT c FROM CheckingAccount c")})
public class CheckingAccount extends Account {
    private static final long serialVersionUID = 1L;
//    @Id
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 9)
//    @Column(name = "ACCOUNTNUMBER")
//    private String accountnumber;
//    
//    @JoinColumn(name = "ACCOUNTNUMBER", referencedColumnName = "ACCOUNTNUMBER", insertable = false, updatable = false)
//    @OneToOne(optional = false)
//    private Account account;

    public CheckingAccount() {
    }

    public CheckingAccount(String accountnumber) {
        super(accountnumber);
    }
    
    public CheckingAccount(String accountnumber, Double interest, Person person){
        super(accountnumber, interest, person);
    }


    @Override
    public String toString() {
        return "dk.cphbusiness.bank.model.CheckingAccount[ accountnumber=" + getAccountNumber() + " ]";
    }
    
}
