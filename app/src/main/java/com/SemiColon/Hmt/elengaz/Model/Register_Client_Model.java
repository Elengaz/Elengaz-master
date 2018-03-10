package com.SemiColon.Hmt.elengaz.Model;

public class Register_Client_Model {

    private Integer success;
    private String message;
    private String client_id;

    public Register_Client_Model() {
    }

    public Register_Client_Model(Integer success, String message, String client_id) {
        this.success = success;
        this.message = message;
        this.client_id = client_id;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}