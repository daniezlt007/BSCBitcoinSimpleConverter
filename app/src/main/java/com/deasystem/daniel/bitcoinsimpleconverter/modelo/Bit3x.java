package com.deasystem.daniel.bitcoinsimpleconverter.modelo;

public class Bit3x {

    private String market;
    private String symbol;
    private double last;
    private double open;
    private double close;
    private double max;
    private double min;
    private double variation;
    private double volume;
    private double exchangeRate;
    private double dollarBrl;
    private double bid;
    private double ask;
    private String marketName;

    public Bit3x() {

    }

    public Bit3x(String market, String symbol, double last, double open, double close, double max, double min, double variation, double volume, double exchangeRate, double dollarBrl, double bid, double ask, String marketName) {
        this.market = market;
        this.symbol = symbol;
        this.last = last;
        this.open = open;
        this.close = close;
        this.max = max;
        this.min = min;
        this.variation = variation;
        this.volume = volume;
        this.exchangeRate = exchangeRate;
        this.dollarBrl = dollarBrl;
        this.bid = bid;
        this.ask = ask;
        this.marketName = marketName;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getVariation() {
        return variation;
    }

    public void setVariation(double variation) {
        this.variation = variation;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public double getDollarBrl() {
        return dollarBrl;
    }

    public void setDollarBrl(double dollarBrl) {
        this.dollarBrl = dollarBrl;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }
}