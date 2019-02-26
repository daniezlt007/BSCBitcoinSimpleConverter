package com.deasystem.daniel.bitcoinsimpleconverter.modelo;

public class Broker {

    private double buy;
    private int date;
    private double high;
    private double last;
    private double low;
    private double sell;
    private double vol;

    public Broker(){

    }

    public Broker(double buy, int date, double high, double last, double low, double sell, double vol) {
        this.buy = buy;
        this.date = date;
        this.high = high;
        this.last = last;
        this.low = low;
        this.sell = sell;
        this.vol = vol;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    public double getVol() {
        return vol;
    }

    public void setVol(double vol) {
        this.vol = vol;
    }
}