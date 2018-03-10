package com.SemiColon.Hmt.elengaz.Model;

/**
 * Created by Elashry on 3/4/2018.
 */



public class DisplayServicesModel {

    private String client_service_id;
    private String client_service_name;
    private String client_service_date;
    private String client_service_end_date;
    private String client_service_cost;
    private String office_name;
    private String state_name;
    private String client_evaluation_state;
    private String my_order_state;

    public String getMy_order_state() {
        return my_order_state;
    }

    public void setMy_order_state(String my_order_state) {
        this.my_order_state = my_order_state;
    }

    public String getClient_service_id() {
        return client_service_id;
    }

    public void setClient_service_id(String client_service_id) {
        this.client_service_id = client_service_id;
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

    public String getClient_service_end_date() {
        return client_service_end_date;
    }

    public void setClient_service_end_date(String client_service_end_date) {
        this.client_service_end_date = client_service_end_date;
    }

    public String getClient_service_cost() {
        return client_service_cost;
    }

    public void setClient_service_cost(String client_service_cost) {
        this.client_service_cost = client_service_cost;
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
