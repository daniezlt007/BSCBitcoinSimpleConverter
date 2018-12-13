package com.deasystem.daniel.bitcoinsimpleconverter.modelo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniel on 07/11/17.
 */

public class RetWalltime {
    private String current_round;
    private String code_version;
    private int order_book_pages;
    private String[] suspended_actions;
    private String order_book_prefix;
    @SerializedName("best_offer")
    private RetWalltime2 retWalltime2;
    //private ArrayList<RetWalltime2> best_offer;

    public RetWalltime(){

    }

    public String getCurrent_round() {
        return current_round;
    }

    public void setCurrent_round(String current_round) {
        this.current_round = current_round;
    }

    public String getCode_version() {
        return code_version;
    }

    public void setCode_version(String code_version) {
        this.code_version = code_version;
    }

    public int getOrder_book_pages() {
        return order_book_pages;
    }

    public void setOrder_book_pages(int order_book_pages) {
        this.order_book_pages = order_book_pages;
    }

    public String[] getSuspended_actions() {
        return suspended_actions;
    }

    public void setSuspended_actions(String[] suspended_actions) {
        this.suspended_actions = suspended_actions;
    }

    public String getOrder_book_prefix() {
        return order_book_prefix;
    }

    public void setOrder_book_prefix(String order_book_prefix) {
        this.order_book_prefix = order_book_prefix;
    }

    public RetWalltime2 getRetWalltime2() {
        return retWalltime2;
    }

    public void setRetWalltime2(RetWalltime2 retWalltime2) {
        this.retWalltime2 = retWalltime2;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
