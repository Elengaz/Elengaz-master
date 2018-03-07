package com.SemiColon.Hmt.elengaz.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Delta on 04/03/2018.
 */

public class OfficeDetailsModel1 implements Serializable {
   @SerializedName("array_detail")
   private List<OfficeDetailsModel> officeDetailsModelList;

   private String message;
    public OfficeDetailsModel1() {
    }


    public OfficeDetailsModel1(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OfficeDetailsModel1(List<OfficeDetailsModel> officeDetailsModelList) {
        this.officeDetailsModelList = officeDetailsModelList;
    }

    public List<OfficeDetailsModel> getOfficeDetailsModelList() {
        return officeDetailsModelList;
    }

    public void setOfficeDetailsModelList(List<OfficeDetailsModel> officeDetailsModelList) {
        this.officeDetailsModelList = officeDetailsModelList;
    }
}
