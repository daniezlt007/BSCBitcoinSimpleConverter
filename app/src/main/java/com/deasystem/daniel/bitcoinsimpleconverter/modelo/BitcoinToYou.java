package com.deasystem.daniel.bitcoinsimpleconverter.modelo;

/**
 * Created by daniel on 19/11/17.
 */

public class BitcoinToYou {

    private double high;
    private double low;
    private double vol;
    private double last;
    private double buy;
    private double buyQty;
    private double sell;
    private double sellQty;
    private int date;

    public BitcoinToYou() {

    }

    public BitcoinToYou(double high, double low, double vol, double last, double buy, double buyQty, double sell, double sellQty, int date) {
        this.high = high;
        this.low = low;
        this.vol = vol;
        this.last = last;
        this.buy = buy;
        this.buyQty = buyQty;
        this.sell = sell;
        this.sellQty = sellQty;
        this.date = date;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getVol() {
        return vol;
    }

    public void setVol(double vol) {
        this.vol = vol;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getBuyQty() {
        return buyQty;
    }

    public void setBuyQty(double buyQty) {
        this.buyQty = buyQty;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    public double getSellQty() {
        return sellQty;
    }

    public void setSellQty(double sellQty) {
        this.sellQty = sellQty;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
