package com.testcase;

import com.test.branch.Branch;
import com.test.branch.BranchService;
import com.test.vehicle.Vehicle;
import com.test.vehicle.VehicleService;
import com.test.vehicle.enums.VehicleType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleTestCase {

    List<Branch> branchList;
    List<Vehicle> vehicleList;
    Branch branch;

    @BeforeEach
    void setup(){

        branchList = new ArrayList<>();
        vehicleList = new ArrayList<>();
        Map<VehicleType, Integer> vehiclesAllowed = new HashMap<>();
        vehiclesAllowed.put(VehicleType.CAR, 2);
        vehiclesAllowed.put(VehicleType.BIKE, 2);
        branch= new Branch("KORMANGALA",  "BANGALORE", vehiclesAllowed);

        Vehicle v = new Vehicle(1, "BR123", "SUV", VehicleType.CAR, 100);
        Vehicle v1 = new Vehicle(1, "BR234", "SEDAN", VehicleType.CAR, 120);
        Vehicle v2 = new Vehicle(1, "BR456", "BULLET", VehicleType.BIKE, 30);
        Vehicle v3 = new Vehicle(1, "BR345", "PULSAR", VehicleType.BIKE, 45);

        vehicleList.add(v);
        vehicleList.add(v1);
        vehicleList.add(v2);
        vehicleList.add(v3);


        Map<VehicleType, Integer> vehiclesBooked = new HashMap<>();
        vehiclesBooked.put(VehicleType.CAR, 2);
        vehiclesBooked.put(VehicleType.BIKE, 2);

        branch.setVehicles(vehicleList);
        branch.setVehiclesBooked(vehiclesBooked);
        branchList.add(branch);
    }

    @AfterEach
    void clear(){
//        branchList = new ArrayList<>();
    }

    @Test
    @DisplayName("Vehicle is valid.")
    public void testValidVehicle() {
       Vehicle v = VehicleService.vehicleExists(vehicleList, "BR123");
        assertEquals(v.getVehicleNumber(), "BR123",
                "Vehicle is valid.");
    }

    @Test
    @DisplayName("Vehicle is in valid.")
    public void testInValidVehicle() {
        Vehicle v = VehicleService.vehicleExists(vehicleList, "BR1233");
        assertEquals(v, null,
                "Vehicle is in valid.");
    }

    @Test
    @DisplayName("Sort Vehicle On Price")
    public void  testVehicleSortingOnPrice(){
        List<Vehicle> vehicles = VehicleService.sortVehicleOnPrice(vehicleList);
        assertEquals(vehicles.get(0).getBasePrice(), 30);
    }

}
