package com.SemiColon.Hmt.elengaz.Model;

/**
 * Created by Elashry on 3/10/2018.
 */


public class Order_State_Model {
    private String client_service_name;
    private String client_service_date;
    private String client_service_status;
    private String office_name;
    private String state_name;
    private String client_evaluation_state;

    public Order_State_Model(String client_service_name, String client_service_date, String client_service_status, String office_name, String state_name, String client_evaluation_state) {
        this.client_service_name = client_service_name;
        this.client_service_date = client_service_date;
        this.client_service_status = client_service_status;
        this.office_name = office_name;
        this.state_name = state_name;
        this.client_evaluation_state = client_evaluation_state;
    }

    public String getClient_service_name() {
        return client_service_name;
    }

    public void setClient_service_name(String client_service_name) {
        this.client_service_name = client_service_name;
    }

    public String getClient_service_date() {
        return client_service_date;
    }

    public void setClient_service_date(String client_service_date) {
        this.client_service_date = client_service_date;
    }

    public String getClient_service_status() {
        return client_service_status;
    }

    public void setClient_service_status(String client_service_status) {
        this.client_service_status = client_service_status;
    }

    public String getOffice_name() {
        return office_name;
    }

    public void setOffice_name(String office_name) {
        this.office_name = office_name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getClient_evaluation_state() {
        return client_evaluation_state;
    }

    public void setClient_evaluation_state(String client_evaluation_state) {
        this.client_evaluation_state = client_evaluation_state;
    }
}
