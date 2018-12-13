package com.deasystem.daniel.bitcoinsimpleconverter.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by daniel on 19/11/17.
 */
public class Brasiliex implements Serializable {

    @SerializedName("active")
    private int active;
    @SerializedName("market")
    private String market;
    @SerializedName("last")
    private double last;
    @SerializedName("percentChange")
    private double percentChange;
    @SerializedName("baseVolume24")
    private double baseVolume24;
    @SerializedName("quoteVolume24")
    private double quoteVolume24;
    @SerializedName("baseVolume")
    private double baseVolume;
    @SerializedName("quoteVolume")
    private double quoteVolume;
    @SerializedName("highestBid24")
    private double highestBid24;
    @SerializedName("lowestAsk24")
    private double lowestAsk24;
    @SerializedName("highestBid")
    private double highestBid;
    @SerializedName("lowestAsk")
    private double lowestAsk;

    public Brasiliex() {

    }

    public Brasiliex(int active, String market, double last, double percentChange, double baseVolume24, double quoteVolume24, double baseVolume, double quoteVolume, double highestBid24, double lowestAsk24, double highestBid, double lowestAsk) {
        this.active = active;
        this.market = market;
        this.last = last;
        this.percentChange = percentChange;
        this.baseVolume24 = baseVolume24;
        this.quoteVolume24 = quoteVolume24;
        this.baseVolume = baseVolume;
        this.quoteVolume = quoteVolume;
        this.highestBid24 = highestBid24;
        this.lowestAsk24 = lowestAsk24;
        this.highestBid = highestBid;
        this.lowestAsk = lowestAsk;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(double percentChange) {
        this.percentChange = percentChange;
    }

    public double getBaseVolume24() {
        return baseVolume24;
    }

    public void setBaseVolume24(double baseVolume24) {
        this.baseVolume24 = baseVolume24;
    }

    public double getQuoteVolume24() {
        return quoteVolume24;
    }

    public void setQuoteVolume24(double quoteVolume24) {
        this.quoteVolume24 = quoteVolume24;
    }

    public double getBaseVolume() {
        return baseVolume;
    }

    public void setBaseVolume(double baseVolume) {
        this.baseVolume = baseVolume;
    }

    public double getQuoteVolume() {
        return quoteVolume;
    }

    public void setQuoteVolume(double quoteVolume) {
        this.quoteVolume = quoteVolume;
    }

    public double getHighestBid24() {
        return highestBid24;
    }

    public void setHighestBid24(double highestBid24) {
        this.highestBid24 = highestBid24;
    }

    public double getLowestAsk24() {
        return lowestAsk24;
    }

    public void setLowestAsk24(double lowestAsk24) {
        this.lowestAsk24 = lowestAsk24;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(double highestBid) {
        this.highestBid = highestBid;
    }

    public double getLowestAsk() {
        return lowestAsk;
    }

    public void setLowestAsk(double lowestAsk) {
        this.lowestAsk = lowestAsk;
    }
}
