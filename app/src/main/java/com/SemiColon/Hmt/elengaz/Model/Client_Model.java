package com.SemiColon.Hmt.elengaz.Model;

import java.io.Serializable;

/**
 * Created by Delta on 08/03/2018.
 */

public class Client_Model implements Serializable {

    private String client_user_name;
    private String client_email;
    private String client_phone;
    private String client_img;
    private String client_id;
    private int total_service;
    private int total_service_remain;


    public Client_Model() {
    }

    public Client_Model(String client_user_name, String client_email, String client_phone, String client_img, String client_id, int total_service, int total_service_remain) {
        this.client_user_name = client_user_name;
        this.client_email = client_email;
        this.client_phone = client_phone;
        this.client_img = client_img;
        this.client_id = client_id;
        this.total_service = total_service;
        this.total_service_remain = total_service_remain;
    }

    public int getTotal_service() {
        return total_service;
    }

    public void setTotal_service(int total_service) {
        this.total_service = total_service;
    }

    public int getTotal_service_remain() {
        return total_service_remain;
    }

    public void setTotal_service_remain(int total_service_remain) {
        this.total_service_remain = total_service_remain;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_user_name() {
        return client_user_name;
    }

    public void setClient_user_name(String client_user_name) {
        this.client_user_name = client_user_name;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public String getClient_phone() {
        return client_phone;
    }

    public void setClient_phone(String client_phone) {
        this.client_phone = client_phone;
    }

    public String getClient_img() {
        return client_img;
    }

    public void setClient_img(String client_img) {
        this.client_img = client_img;
    }
}
