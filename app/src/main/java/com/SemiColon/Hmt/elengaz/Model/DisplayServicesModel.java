package com.SemiColon.Hmt.elengaz.Model;

import java.io.Serializable;

/**
 * Created by Elashry on 3/4/2018.
 */



public class DisplayServicesModel implements Serializable {

    private String client_service_id;
    private String client_service_name;
    private String client_service_date;
    private String client_service_end_date;
    private String client_service_details;
    private String client_service_cost;
    private String client_service_status;
    private String client_evaluation;
    private String office_name;
    private String state_name;
    private String client_evaluation_state;
    private int my_order_state;
    private String transfer_status;
    private String total_send;
    private String service_closed;

    public DisplayServicesModel() {
    }

    public DisplayServicesModel(String client_service_id, String client_service_name, String client_service_date, String client_service_end_date, String client_service_details, String client_service_cost, String client_service_status, String client_evaluation, String office_name, String state_name, String client_evaluation_state, int my_order_state, String transfer_status, String total_send, String service_closed) {
        this.client_service_id = client_service_id;
        this.client_service_name = client_service_name;
        this.client_service_date = client_service_date;
        this.client_service_end_date = client_service_end_date;
        this.client_service_details = client_service_details;
        this.client_service_cost = client_service_cost;
        this.client_service_status = client_service_status;
        this.client_evaluation = client_evaluation;
        this.office_name = office_name;
        this.state_name = state_name;
        this.client_evaluation_state = client_evaluation_state;
        this.my_order_state = my_order_state;
        this.transfer_status = transfer_status;
        this.total_send = total_send;
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

    public String getClient_service_details() {
        return client_service_details;
    }

    public void setClient_service_details(String client_service_details) {
        this.client_service_details = client_service_details;
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

    public String getClient_evaluation() {
        return client_evaluation;
    }

    public void setClient_evaluation(String client_evaluation) {
        this.client_evaluation = client_evaluation;
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

    public int getMy_order_state() {
        return my_order_state;
    }

    public void setMy_order_state(int my_order_state) {
        this.my_order_state = my_order_state;
    }

    public String getTransfer_status() {
        return transfer_status;
    }

    public void setTransfer_status(String transfer_status) {
        this.transfer_status = transfer_status;
    }

    public String getTotal_send() {
        return total_send;
    }

    public void setTotal_send(String total_send) {
        this.total_send = total_send;
    }
}
