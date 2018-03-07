package com.SemiColon.Hmt.elengaz.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Elashry on 3/3/2018.
 */



public class office_order_model {


        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("client_service_id_fk")
        @Expose
        private String clientServiceIdFk;
        @SerializedName("office_id_fk")
        @Expose
        private String officeIdFk;
        @SerializedName("client_id_fk")
        @Expose
        private String clientIdFk;
        @SerializedName("client_service_name")
        @Expose
        private String clientServiceName;
        @SerializedName("client_service_details")
        @Expose
        private String clientServiceDetails;
        @SerializedName("client_phone_number")
        @Expose
        private String clientPhoneNumber;
        @SerializedName("client_email_account")
        @Expose
        private String clientEmailAccount;

        @SerializedName("client_other_phone")
        @Expose
        private String clientOtherPhone;
        @SerializedName("client_user_name")
        @Expose
        private String clientUserName;
    @SerializedName("message")
    @Expose
    private String message;

    public office_order_model(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getClientServiceIdFk() {
            return clientServiceIdFk;
        }

        public void setClientServiceIdFk(String clientServiceIdFk) {
            this.clientServiceIdFk = clientServiceIdFk;
        }

        public String getOfficeIdFk() {
            return officeIdFk;
        }

        public void setOfficeIdFk(String officeIdFk) {
            this.officeIdFk = officeIdFk;
        }

        public String getClientIdFk() {
            return clientIdFk;
        }

        public void setClientIdFk(String clientIdFk) {
            this.clientIdFk = clientIdFk;
        }

        public String getClientServiceName() {
            return clientServiceName;
        }

        public void setClientServiceName(String clientServiceName) {
            this.clientServiceName = clientServiceName;
        }

        public String getClientServiceDetails() {
            return clientServiceDetails;
        }

        public void setClientServiceDetails(String clientServiceDetails) {
            this.clientServiceDetails = clientServiceDetails;
        }

        public String getClientPhoneNumber() {
            return clientPhoneNumber;
        }

        public void setClientPhoneNumber(String clientPhoneNumber) {
            this.clientPhoneNumber = clientPhoneNumber;
        }

        public String getClientEmailAccount() {
            return clientEmailAccount;
        }

        public void setClientEmailAccount(String clientEmailAccount) {
            this.clientEmailAccount = clientEmailAccount;
        }

        public String getClientOtherPhone() {
            return clientOtherPhone;
        }

        public void setClientOtherPhone(String clientOtherPhone) {
            this.clientOtherPhone = clientOtherPhone;
        }

        public String getClientUserName() {
            return clientUserName;
        }

        public void setClientUserName(String clientUserName) {
            this.clientUserName = clientUserName;
        }

    }
