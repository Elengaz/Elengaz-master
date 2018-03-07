package com.SemiColon.Hmt.elengaz.Model;

import java.io.Serializable;

/**
 * Created by Delta on 04/03/2018.
 */

public class Comments implements Serializable {
    private String client_id_fk;
    private String service_id_fk;
    private String client_service_name;
    private String client_service_date;
    private String client_service_status;
    private String client_evaluation;
    private String client_evaluation_comment;
    private String client_user_name;
    private String client_id;

    public Comments() {
    }

    public Comments(String client_id_fk, String service_id_fk, String client_service_name, String client_service_date, String client_service_status, String client_evaluation, String client_evaluation_comment, String client_user_name, String client_id) {
        this.client_id_fk = client_id_fk;
        this.service_id_fk = service_id_fk;
        this.client_service_name = client_service_name;
        this.client_service_date = client_service_date;
        this.client_service_status = client_service_status;
        this.client_evaluation = client_evaluation;
        this.client_evaluation_comment = client_evaluation_comment;
        this.client_user_name = client_user_name;
        this.client_id = client_id;
    }

    public String getClient_id_fk() {
        return client_id_fk;
    }

    public void setClient_id_fk(String client_id_fk) {
        this.client_id_fk = client_id_fk;
    }

    public String getService_id_fk() {
        return service_id_fk;
    }

    public void setService_id_fk(String service_id_fk) {
        this.service_id_fk = service_id_fk;
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

    public String getClient_evaluation() {
        return client_evaluation;
    }

    public void setClient_evaluation(String client_evaluation) {
        this.client_evaluation = client_evaluation;
    }

    public String getClient_evaluation_comment() {
        return client_evaluation_comment;
    }

    public void setClient_evaluation_comment(String client_evaluation_comment) {
        this.client_evaluation_comment = client_evaluation_comment;
    }

    public String getClient_user_name() {
        return client_user_name;
    }

    public void setClient_user_name(String client_user_name) {
        this.client_user_name = client_user_name;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
