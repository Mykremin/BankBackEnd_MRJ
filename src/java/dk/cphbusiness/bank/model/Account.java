/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.cphbusiness.bank.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Mykremin
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
@Table(name = "ACCOUNT")
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")})
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 32)
    @Column(name = "DTYPE")
    private String dtype;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "ACCOUNTNUMBER")
    private String accountNumber;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "BALANCE")
    private double balance;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "INTEREST")
    private double interest;
    
    @OneToMany(mappedBy = "source")
    private Collection<Transfer> outgoing;
    
    @OneToMany(mappedBy = "target")
    private Collection<Transfer> incoming;
    
    @JoinColumn(name = "CPR", referencedColumnName = "CPR")
    @ManyToOne
    private Person cpr;

    public Account() {
    }

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Account(String accountNumber, double interest, Person cpr) {
        this.accountNumber = accountNumber;
        this.interest = interest;
        this.cpr = cpr;
    }

    

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }
    
    
    public Person getCpr() {
        return cpr;
    }

    public void setCpr(Person cpr) {
        this.cpr = cpr;
    }

    public Collection<Transfer> getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(Collection<Transfer> outgoing) {
        this.outgoing = outgoing;
    }

    public Collection<Transfer> getIncoming() {
        return incoming;
    }

    public void setIncoming(Collection<Transfer> incoming) {
        this.incoming = incoming;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountNumber != null ? accountNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.accountNumber == null && other.accountNumber != null) || (this.accountNumber != null && !this.accountNumber.equals(other.accountNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dk.cphbusiness.bank.model.Account[ accountnumber=" + accountNumber + " ]";
    }
    
}
