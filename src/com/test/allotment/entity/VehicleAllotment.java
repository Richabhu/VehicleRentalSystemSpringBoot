package com.test.allotment.entity;

import com.test.customer.Customer;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class VehicleAllotment {

    private static final AtomicInteger counter = new AtomicInteger();

    private int allotmentId;
    private int vehicleId;
    private int startTime;
    private int endTime;
    private Date date;
    private Customer customer;

    public VehicleAllotment( int vehicleId, int startTime, int endTime, Date date, Customer customer) {
        this.allotmentId = counter.incrementAndGet();
        this.vehicleId = vehicleId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.customer = customer;
    }

    public int getAllotmentId() {
        return allotmentId;
    }

    public void setAllotmentId(int allotmentId) {
        this.allotmentId = allotmentId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}
