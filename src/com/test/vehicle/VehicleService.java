package com.test.vehicle;

import com.test.allotment.entity.VehicleAllotment;
import com.test.allotment.service.VehicleAllotmentService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VehicleService {

    public static Vehicle vehicleExists(List<Vehicle> vehicles, String vehicleNo){
        for(Vehicle vehicle: vehicles){
            if(vehicle.getVehicleNumber().equals(vehicleNo))
                return vehicle;
        }
        return null;
    }

    public static List<Vehicle> sortVehicleOnPrice(List<Vehicle> vehicles)
    {
        Collections.sort(vehicles, Comparator.comparing(Vehicle::getBasePrice));
        return vehicles;
    }

    public static void displayAvailableVehicles(List<Vehicle> vehicles, List<VehicleAllotment> vehicleAllotments){
        System.out.println("Vehicle Availability");

        vehicles = VehicleService.sortVehicleOnPrice(vehicles);
        System.out.println("Vehicle Name        Vehicle Number       Base Price         Availability");
        for(Vehicle v: vehicles){
            System.out.println(v.getVehicleName() +" " + v.getVehicleNumber() +" "+ v.getBasePrice()+" "+
                    VehicleAllotmentService.getVehicleAvailability(vehicleAllotments, v));
        }
    }
}
