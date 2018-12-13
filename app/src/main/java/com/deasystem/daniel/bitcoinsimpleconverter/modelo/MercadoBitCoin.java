package com.deasystem.daniel.bitcoinsimpleconverter.modelo;

/**
 * Created by daniel on 22/10/17.
 */

public class MercadoBitCoin {
//{"ticker": { "high": "18847.00000000", "low": "18050.00000000", "vol": "155.35057408", "last": "18501.04002000", "buy": "18503.00000000", "sell": "18648.99999000", "date": 1508708385}}
    private double high;
    private double low;
    private double vol;
    private double last;
    private double buy;
    private double sell;
    private int date;

    public MercadoBitCoin(){

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

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
