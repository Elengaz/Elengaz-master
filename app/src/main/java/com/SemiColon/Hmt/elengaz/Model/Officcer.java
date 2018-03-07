package com.SemiColon.Hmt.elengaz.Model;

/**
 * Created by Elashry on 2/12/2018.
 */

public class Officcer {

    private String office_user_name;
    private String office_password;
    private String office_email;
    private String office_phone;
    private String office_title;
    private String office_city;
    private String office_area;

    public Officcer(String office_user_name, String office_password, String office_email, String office_phone, String office_title, String office_city, String office_area) {
        this.office_user_name = office_user_name;
        this.office_password = office_password;
        this.office_email = office_email;
        this.office_phone = office_phone;
        this.office_title = office_title;
        this.office_city = office_city;
        this.office_area = office_area;
    }

    public String getOffice_area() {
        return office_area;
    }

    public void setOffice_area(String office_area) {
        this.office_area = office_area;
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

    public String getOffice_title() {
        return office_title;
    }

    public void setOffice_title(String office_title) {
        this.office_title = office_title;
    }

    public String getOffice_city() {
        return office_city;
    }

    public void setOffice_city(String office_city) {
        this.office_city = office_city;
    }
}
