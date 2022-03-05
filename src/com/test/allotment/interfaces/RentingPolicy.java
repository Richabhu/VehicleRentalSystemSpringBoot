package com.test.allotment.interfaces;

import com.test.allotment.entity.VehicleAllotment;
import com.test.vehicle.Vehicle;
import com.test.vehicle.enums.VehicleType;

import java.util.List;

public interface RentingPolicy {

    Vehicle selectVehicle(List<Vehicle> vehicles, int startTime, int endTime, VehicleType type,
                          List<VehicleAllotment> vehicleAllotments);

}