package com.SemiColon.Hmt.elengaz.Model;

import java.io.Serializable;

/**
 * Created by Delta on 11/03/2018.
 */

public class AddServicesResponse implements Serializable {

    private int success;
    private String message;
    private String client_service_id;

    public AddServicesResponse() {
    }

    public AddServicesResponse(int success, String message, String client_service_id) {
        this.success = success;
        this.message = message;
        this.client_service_id = client_service_id;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClient_service_id() {
        return client_service_id;
    }

    public void setClient_service_id(String client_service_id) {
        this.client_service_id = client_service_id;
    }
}
