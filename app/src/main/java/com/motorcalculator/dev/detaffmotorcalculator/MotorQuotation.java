package com.motorcalculator.dev.detaffmotorcalculator;

/**
 * Created by dev on 11/12/17.
 */

public class MotorQuotation {

    private String message;
    private String status;
    private double basic_contribution;
    private double gross_contribution;
    private double contri_after_rebate;
    private double gst;
    private double total_contribution;

    public MotorQuotation() {

        this.message = "" ;
        this.status = "";
        this.basic_contribution = 0;
        this.gross_contribution = 0;
        this.contri_after_rebate = 0;
        this.gst = 0;
        this.total_contribution = 0;
    }

    public MotorQuotation(String message, String status, double basic_contribution, double gross_contribution, double contri_after_rebate, double gst, double total_contribution) {
        this.message = message;
        this.status = status;
        this.basic_contribution = basic_contribution;
        this.gross_contribution = gross_contribution;
        this.contri_after_rebate = contri_after_rebate;
        this.gst = gst;
        this.total_contribution = total_contribution;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBasic_contribution() {
        return basic_contribution;
    }

    public void setBasic_contribution(double basic_contribution) {
        this.basic_contribution = basic_contribution;
    }

    public double getGross_contribution() {
        return gross_contribution;
    }

    public void setGross_contribution(double gross_contribution) {
        this.gross_contribution = gross_contribution;
    }

    public double getContri_after_rebate() {
        return contri_after_rebate;
    }

    public void setContri_after_rebate(double contri_after_rebate) {
        this.contri_after_rebate = contri_after_rebate;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getTotal_contribution() {
        return total_contribution;
    }

    public void setTotal_contribution(double total_contribution) {
        this.total_contribution = total_contribution;
    }
}
