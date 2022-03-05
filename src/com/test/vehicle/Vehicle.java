package com.test.vehicle;

import com.test.vehicle.enums.VehicleType;

import java.util.concurrent.atomic.AtomicInteger;

public class Vehicle {

    private static final AtomicInteger counter = new AtomicInteger();

    private int branchId;
    private int vehicleId;
    private String vehicleNumber;
    private String vehicleName;
    private VehicleType type;
    private double basePrice;
    private double rating;

    public Vehicle(int branchId, String vehicleNumber, String vehicleName, VehicleType type, double basePrice) {
        this.branchId = branchId;
        this.vehicleId = counter.incrementAndGet();
        this.vehicleNumber = vehicleNumber;
        this.vehicleName = vehicleName;
        this.type = type;
        this.basePrice = basePrice;
        this.rating = 0;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
