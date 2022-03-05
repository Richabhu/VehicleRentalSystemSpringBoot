package com.test.allotment.service;

import com.test.allotment.entity.VehicleAllotment;
import com.test.customer.Customer;
import com.test.vehicle.Vehicle;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VehicleAllotmentService {


    public static boolean checkVehicleAvailable(List<VehicleAllotment> vehicleAllotments, Vehicle vehicle,
                                                int startTime, int endTime){

        List<Pair<Integer, Integer>> busyTime = VehicleAllotmentService.getVehicleAllotment(vehicleAllotments, vehicle);

        // check whether requested time overlap already booked time
        for(Pair<Integer, Integer>  time: busyTime){
            if((startTime >= time.getKey() && startTime <= time.getValue()) &&
                    (endTime >=time.getKey() && endTime <=time.getValue()))
                return false;

        }
        return true;
    }

    public static VehicleAllotment bookVehicle(Vehicle v, int startTime, int endTime, Customer customer){

        VehicleAllotment vehicleAllotment = new VehicleAllotment(v.getVehicleId(), startTime,
                endTime,new Date(),customer);
        return vehicleAllotment;
    }

    public static List<Pair<Integer, Integer>> getVehicleAllotment(List<VehicleAllotment> vehicleAllotments, Vehicle v){
        List<Pair<Integer, Integer>> busyTime = new ArrayList<>();
        // fetch already allotted time of vehicle
        for(VehicleAllotment allotment: vehicleAllotments){

            if(allotment.getVehicleId() == v.getVehicleId()){
                busyTime.add(new Pair<>(allotment.getStartTime(), allotment.getEndTime()));
            }
        }
        return busyTime;
    }

    public static List<Pair<Integer, Integer>> getVehicleAvailability(List<VehicleAllotment> vehicleAllotments, Vehicle v){

        List<Pair<Integer, Integer>> busyTime = VehicleAllotmentService.getVehicleAllotment(vehicleAllotments, v);

        List<Pair<Integer, Integer>> availability = new ArrayList<>();

        if(busyTime.size() == 0){
            availability.add(new Pair(0, 24));
            return availability;
        }
        int count = 1;
        int startTime=0, endTime;
        for(Pair<Integer, Integer> allotted: busyTime){


            if(allotted.getKey() > 0 && count == 1){
                startTime = 0;
                endTime = allotted.getKey();
                availability.add(new Pair(startTime, endTime));
                startTime = endTime;
                count++;
            }
            else if(count == 1 && allotted.getKey() == 0)
            {
                startTime = allotted.getValue();
                count++;
            }
            else{
                endTime = allotted.getKey();
                availability.add(new Pair(startTime, endTime));
                startTime = allotted.getValue();
            }
        }
        if(startTime != 24)
        {
            availability.add(new Pair(startTime, 24));
        }

        return availability;
    }
}
