package com.SemiColon.Hmt.elengaz.Model;

import java.io.Serializable;

/**
 * Created by Elashry on 2/24/2018.
 */

public class Officces implements Serializable {


    private String office_id;
    private String office_title;
    private  Integer success;
    private String client_service_id;

    public Officces(String office_id, String office_title) {
        this.office_id = office_id;
        this.office_title = office_title;
    }

    public String getClient_service_id() {
        return client_service_id;
    }

    public void setClient_service_id(String client_service_id) {
        this.client_service_id = client_service_id;
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

    public String getOffice_title() {
        return office_title;
    }

    public void setOffice_title(String office_title) {
        this.office_title = office_title;
    }
}
