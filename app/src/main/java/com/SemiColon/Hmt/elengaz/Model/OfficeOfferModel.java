package com.SemiColon.Hmt.elengaz.Model;

import java.io.Serializable;

/**
 * Created by Delta on 27/02/2018.
 */

public class OfficeOfferModel implements Serializable {

    private String message_id;
    private String client_service_id_fk;
    private String office_id_fk;
    private String client_id_fk;
    private String office_service_cost;
    private String messages_date;
    private String messages_date_s;
    private String office_title;

    public OfficeOfferModel() {
    }

    public OfficeOfferModel(String message_id, String client_service_id_fk, String office_id_fk, String client_id_fk, String office_service_cost, String messages_date, String messages_date_s, String office_title) {
        this.message_id = message_id;
        this.client_service_id_fk = client_service_id_fk;
        this.office_id_fk = office_id_fk;
        this.client_id_fk = client_id_fk;
        this.office_service_cost = office_service_cost;
        this.messages_date = messages_date;
        this.messages_date_s = messages_date_s;
        this.office_title = office_title;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getClient_service_id_fk() {
        return client_service_id_fk;
    }

    public void setClient_service_id_fk(String client_service_id_fk) {
        this.client_service_id_fk = client_service_id_fk;
    }

    public String getOffice_id_fk() {
        return office_id_fk;
    }

    public void setOffice_id_fk(String office_id_fk) {
        this.office_id_fk = office_id_fk;
    }

    public String getClient_id_fk() {
        return client_id_fk;
    }

    public void setClient_id_fk(String client_id_fk) {
        this.client_id_fk = client_id_fk;
    }

    public String getOffice_service_cost() {
        return office_service_cost;
    }

    public void setOffice_service_cost(String office_service_cost) {
        this.office_service_cost = office_service_cost;
    }

    public String getMessages_date() {
        return messages_date;
    }

    public void setMessages_date(String messages_date) {
        this.messages_date = messages_date;
    }

    public String getMessages_date_s() {
        return messages_date_s;
    }

    public void setMessages_date_s(String messages_date_s) {
        this.messages_date_s = messages_date_s;
    }

    public String getOffice_title() {
        return office_title;
    }

    public void setOffice_title(String office_title) {
        this.office_title = office_title;
    }
}
