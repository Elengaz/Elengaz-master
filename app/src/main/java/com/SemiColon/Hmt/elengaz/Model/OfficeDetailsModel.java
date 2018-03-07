package com.SemiColon.Hmt.elengaz.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Delta on 04/03/2018.
 */

public class OfficeDetailsModel implements Serializable {
   private String office_id;
   private String office_user_name;
   private String office_city;
   private String office_title;
   private String office_evaluation_count;
   private String office_clients_count;
   private int total_service;
   private int finished_service;
   private int rate_evaluation;
   private int star_1_count;
   private int star_2_count;
   private int star_3_count;
   private int star_4_count;
   private int star_5_count;
   @SerializedName("all_evaluation")
   private List<Comments> commentsList;


    public OfficeDetailsModel() {
    }

    public OfficeDetailsModel(String office_id, String office_user_name, String office_city, String office_title, String office_evaluation_count, String office_clients_count, int total_service, int finished_service, int rate_evaluation, int star_1_count, int star_2_count, int star_3_count) {
        this.office_id = office_id;
        this.office_user_name = office_user_name;
        this.office_city = office_city;
        this.office_title = office_title;
        this.office_evaluation_count = office_evaluation_count;
        this.office_clients_count = office_clients_count;
        this.total_service = total_service;
        this.finished_service = finished_service;
        this.rate_evaluation = rate_evaluation;
        this.star_1_count = star_1_count;
        this.star_2_count = star_2_count;
        this.star_3_count = star_3_count;
    }

    public String getOffice_id() {
        return office_id;
    }

    public void setOffice_id(String office_id) {
        this.office_id = office_id;
    }

    public String getOffice_user_name() {
        return office_user_name;
    }

    public void setOffice_user_name(String office_user_name) {
        this.office_user_name = office_user_name;
    }

    public String getOffice_city() {
        return office_city;
    }

    public void setOffice_city(String office_city) {
        this.office_city = office_city;
    }

    public String getOffice_title() {
        return office_title;
    }

    public void setOffice_title(String office_title) {
        this.office_title = office_title;
    }

    public String getOffice_evaluation_count() {
        return office_evaluation_count;
    }

    public void setOffice_evaluation_count(String office_evaluation_count) {
        this.office_evaluation_count = office_evaluation_count;
    }

    public String getOffice_clients_count() {
        return office_clients_count;
    }

    public void setOffice_clients_count(String office_clients_count) {
        this.office_clients_count = office_clients_count;
    }

    public int getTotal_service() {
        return total_service;
    }

    public void setTotal_service(int total_service) {
        this.total_service = total_service;
    }

    public int getFinished_service() {
        return finished_service;
    }

    public void setFinished_service(int finished_service) {
        this.finished_service = finished_service;
    }

    public int getRate_evaluation() {
        return rate_evaluation;
    }

    public void setRate_evaluation(int rate_evaluation) {
        this.rate_evaluation = rate_evaluation;
    }

    public int getStar_1_count() {
        return star_1_count;
    }

    public void setStar_1_count(int star_1_count) {
        this.star_1_count = star_1_count;
    }

    public int getStar_2_count() {
        return star_2_count;
    }

    public void setStar_2_count(int star_2_count) {
        this.star_2_count = star_2_count;
    }

    public int getStar_3_count() {
        return star_3_count;
    }

    public void setStar_3_count(int star_3_count) {
        this.star_3_count = star_3_count;
    }

    public int getStar_4_count() {
        return star_4_count;
    }

    public void setStar_4_count(int star_4_count) {
        this.star_4_count = star_4_count;
    }

    public int getStar_5_count() {
        return star_5_count;
    }

    public void setStar_5_count(int star_5_count) {
        this.star_5_count = star_5_count;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }
}
