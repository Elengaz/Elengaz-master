package com.SemiColon.Hmt.elengaz.Model;

/**
 * Created by Elashry on 2/27/2018.
 */

public class ProfileModel {

    private String office_id;
    private String office_user_name;
    private String office_password;
    private String office_email;
    private String office_phone;
    private String office_city;
    private String office_title;
    private Integer success;
    private String office_logo;
    public ProfileModel() {
    }

    public ProfileModel(String office_id, String office_user_name, String office_password, String office_email, String office_phone, String office_city, String office_title, Integer success, String office_logo) {
        this.office_id = office_id;
        this.office_user_name = office_user_name;
        this.office_password = office_password;
        this.office_email = office_email;
        this.office_phone = office_phone;
        this.office_city = office_city;
        this.office_title = office_title;
        this.success = success;
        this.office_logo = office_logo;
    }

    public String getOffice_logo() {
        return office_logo;
    }

    public void setOffice_logo(String office_logo) {
        this.office_logo = office_logo;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getOffice_id() {
        return office_id;
    }

    public void setOffice_id(String office_id) {
        this.office_id = office_id;
    }

    public String getOffice_user_name() {
        return office_user_name;
    }

    public void setOffice_user_name(String office_user_name) {
        this.office_user_name = office_user_name;
    }

    public String getOffice_password() {
        return office_password;
    }

    public void setOffice_password(String office_password) {
        this.office_password = office_password;
    }

    public String getOffice_email() {
        return office_email;
    }

    public void setOffice_email(String office_email) {
        this.office_email = office_email;
    }

    public String getOffice_phone() {
        return office_phone;
    }

    public void setOffice_phone(String office_phone) {
        this.office_phone = office_phone;
    }

    public String getOffice_city() {
        return office_city;
    }

    public void setOffice_city(String office_city) {
        this.office_city = office_city;
    }

    public String getOffice_title() {
        return office_title;
    }

    public void setOffice_title(String office_title) {
        this.office_title = office_title;
    }
}
