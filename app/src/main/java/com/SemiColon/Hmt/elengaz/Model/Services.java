package com.SemiColon.Hmt.elengaz.Model;

import java.io.Serializable;

/**
 * Created by Elashry on 2/10/2018.
 */

public class Services implements Serializable{
    private String service_id;
    private String service_title;
    private Integer success;
    private String client_service_id;
    private String category_id;

    public Services(String service_id, String service_title) {
        this.service_id = service_id;
        this.service_title = service_title;
    }

    public Services() {
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_title() {
        return service_title;
    }

    public void setService_title(String service_title) {
        this.service_title = service_title;
    }
}
