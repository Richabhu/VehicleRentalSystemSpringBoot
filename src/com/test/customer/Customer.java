package com.test.customer;

import java.util.concurrent.atomic.AtomicInteger;

public class Customer {

    private static final AtomicInteger counter = new AtomicInteger();

    private int customerId;
    private String name;
    private long phone;
    private String email;
    private double fare;

    private BookingStatus status;

    public Customer( String name, long phone, String email) {
        this.customerId = counter.incrementAndGet();
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
