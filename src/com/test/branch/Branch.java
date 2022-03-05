package com.test.branch;


import com.test.vehicle.Vehicle;
import com.test.vehicle.enums.VehicleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Branch {

    private static final AtomicInteger counter = new AtomicInteger();

    private int branchId;
    private String branchName;
    private String city;
    private Map<VehicleType, Integer> vehiclesAllowed;
    private Map<VehicleType, Integer> vehiclesBooked;
    private List<Vehicle> vehicles;


    public Branch( String branchName, String city,Map<VehicleType, Integer> vehiclesAllowed ) {
        this.branchId = counter.incrementAndGet();;
        this.branchName = branchName;
        this.city = city;
        this.vehiclesAllowed = vehiclesAllowed;

        this.vehiclesBooked = new HashMap<>();
        vehiclesBooked.put(VehicleType.BIKE, 0);
        vehiclesBooked.put(VehicleType.CAR, 0);
    }

    public int getBranchId() {
        return branchId;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Map<VehicleType, Integer> getVehiclesAllowed() {
        return vehiclesAllowed;
    }

    public void setVehiclesAllowed(Map<VehicleType, Integer> vehiclesAllowed) {
        this.vehiclesAllowed = vehiclesAllowed;
    }

    public Map<VehicleType, Integer> getVehiclesBooked() {
        return vehiclesBooked;
    }

    public void setVehiclesBooked(Map<VehicleType, Integer> vehiclesBooked) {
        this.vehiclesBooked = vehiclesBooked;
    }
}
