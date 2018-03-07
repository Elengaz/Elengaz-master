package com.SemiColon.Hmt.elengaz.Model;

/**
 * Created by Elashry on 2/27/2018.
 */

public class Bank_Account_Model {

    private String account_name;
    private String account_bank_name;
    private String account_number;
    private String account_IBAN;

    public Bank_Account_Model() {
    }

    public Bank_Account_Model(String account_name, String account_bank_name, String account_number, String account_IBAN) {
        this.account_name = account_name;
        this.account_bank_name = account_bank_name;
        this.account_number = account_number;
        this.account_IBAN = account_IBAN;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_bank_name() {
        return account_bank_name;
    }

    public void setAccount_bank_name(String account_bank_name) {
        this.account_bank_name = account_bank_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_IBAN() {
        return account_IBAN;
    }

    public void setAccount_IBAN(String account_IBAN) {
        this.account_IBAN = account_IBAN;
    }
}
