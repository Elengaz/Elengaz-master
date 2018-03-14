package com.SemiColon.Hmt.elengaz.Model;

/**
 * Created by Elashry on 3/8/2018.
 */



public class Office_Service_Model {

    private String client_service_id;
    private String client_service_name;
    private String client_service_details;
    private String client_service_cost;
    private String client_service_status;
    private String client_phone_number;
    private String state_name;
    private String service_closed;

    public Office_Service_Model() {
    }

    public Office_Service_Model(String client_service_id, String client_service_name, String client_service_details, String client_service_cost, String client_service_status, String client_phone_number, String state_name, String service_closed) {
        this.client_service_id = client_service_id;
        this.client_service_name = client_service_name;
        this.client_service_details = client_service_details;
        this.client_service_cost = client_service_cost;
        this.client_service_status = client_service_status;
        this.client_phone_number = client_phone_number;
        this.state_name = state_name;
        this.service_closed = service_closed;
    }

    public String getService_closed() {
        return service_closed;
    }

    public void setService_closed(String service_closed) {
        this.service_closed = service_closed;
    }

    public String getClient_service_id() {
        return client_service_id;
    }

    public void setClient_service_id(String client_service_id) {
        this.client_service_id = client_service_id;
    }

    public String getClient_service_details() {
        return client_service_details;
    }

    public void setClient_service_details(String client_service_details) {
        this.client_service_details = client_service_details;
    }

    public String getClient_service_name() {
        return client_service_name;
    }

    public void setClient_service_name(String client_service_name) {
        this.client_service_name = client_service_name;
    }

    public String getClient_service_cost() {
        return client_service_cost;
    }

    public void setClient_service_cost(String client_service_cost) {
        this.client_service_cost = client_service_cost;
    }

    public String getClient_service_status() {
        return client_service_status;
    }

    public void setClient_service_status(String client_service_status) {
        this.client_service_status = client_service_status;
    }

    public String getClient_phone_number() {
        return client_phone_number;
    }

    public void setClient_phone_number(String client_phone_number) {
        this.client_phone_number = client_phone_number;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }
}
