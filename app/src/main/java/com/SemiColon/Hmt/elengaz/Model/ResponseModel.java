package com.SemiColon.Hmt.elengaz.Model;

import java.io.Serializable;

/**
 * Created by Delta on 28/02/2018.
 */

public class ResponseModel implements Serializable {
    private int success;
    private String message;

    public ResponseModel() {
    }

    public ResponseModel(int success, String message) {
        this.success = success;
        this.message = message;
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
}
