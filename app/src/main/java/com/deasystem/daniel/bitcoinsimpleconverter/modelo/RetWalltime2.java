package com.deasystem.daniel.bitcoinsimpleconverter.modelo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniel on 07/11/17.
 */

public class RetWalltime2 {
    @SerializedName("brl-xbt")
    private String brl_xbt;

    @SerializedName("xbt-brl")
    private String xbt_brl;

    public RetWalltime2(){

    }

    @SerializedName("brl_xbt")
    public String getBrl_xbt() {
        return brl_xbt;
    }

    @SerializedName("brl_xbt")
    public void setBrl_xbt(String brl_xbt) {
        this.brl_xbt = brl_xbt;
    }

    @SerializedName("xbt-brl")
    public String getXbt_brl() {
        return xbt_brl;
    }

    @SerializedName("xbt-brl")
    public void setXbt_brl(String xbt_brl) {
        this.xbt_brl = xbt_brl;
    }
}
