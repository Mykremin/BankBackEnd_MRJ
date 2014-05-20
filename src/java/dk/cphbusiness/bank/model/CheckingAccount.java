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

    public CheckingAccount() {
    }

    public CheckingAccount(String accountnumber) {
        super(accountnumber);
    }

    @Override
    public String toString() {
        return "dk.cphbusiness.bank.model.CheckingAccount[ accountnumber=" + getAccountNumber() + " ]";
    }
    
}
