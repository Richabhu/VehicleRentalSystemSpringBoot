package com.test.allotment.service;

import com.test.allotment.entity.VehicleAllotment;
import com.test.allotment.interfaces.RentingPolicy;
import com.test.vehicle.Vehicle;
import com.test.vehicle.enums.VehicleType;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LowestVehiclePrice implements RentingPolicy {

    @Override
    public Vehicle selectVehicle(List<Vehicle> vehicles, int startTime, int endTime, VehicleType type,
                                 List<VehicleAllotment> vehicleAllotments) {

        Collections.sort(vehicles, Comparator.comparing(Vehicle::getType).thenComparing(Vehicle::getBasePrice));
        //todo: check vehicle available on time frame

        for(Vehicle v: vehicles){
            if(v.getType().equals(type)){
                if(VehicleAllotmentService.checkVehicleAvailable(vehicleAllotments, v, startTime, endTime)){
                    return v;
                }
            }

        }

        return null;
    }

    public Vehicle rentVehicle(List<Vehicle> vehicles, int startTime, int endTime, VehicleType type,
                               List<VehicleAllotment> vehicleAllotments){

        Vehicle vehicle = this.selectVehicle(vehicles, startTime, endTime, type, vehicleAllotments);

        if(vehicle == null)
            return null;

        return vehicle;

    }

}
