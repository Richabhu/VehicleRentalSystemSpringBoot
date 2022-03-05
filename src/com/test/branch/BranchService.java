package com.test.branch;

import com.test.allotment.entity.VehicleAllotment;
import com.test.vehicle.Vehicle;
import com.test.vehicle.VehicleService;
import com.test.vehicle.enums.VehicleType;

import java.util.List;

public class BranchService {

    public static Branch validBranch(List<Branch> branches, String branchName){
        for(Branch b: branches){
            if(b.getBranchName().equals(branchName))
                return b;

        }
        return null;
    }

    public static Branch fetchBranchOnId(List<Branch> branches, int branchId){

        for(Branch b: branches){
            if(b.getBranchId() == branchId){
                return b;
            }
        }
        return null;
    }

    public static double calculateSurge(Branch branch, double basePay, VehicleType type, double surgePer){
        double surge = 0;


        double vehicleBooked = (branch.getVehiclesBooked().get(type) * 1.0/ branch.getVehiclesAllowed().get(type) )* 100;

        if(vehicleBooked >= 80){
            surge = surgePer/100 * basePay;
        }

        return surge;
    }


    public static void displayVehicleOnBranch(Branch b, List<Vehicle> vehicles, List<VehicleAllotment> vehicleAllotments){
        VehicleService.displayAvailableVehicles(b.getVehicles(), vehicleAllotments);
    }
}
