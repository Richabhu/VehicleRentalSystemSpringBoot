package com.testcase;

import com.test.allotment.entity.VehicleAllotment;
import com.test.allotment.service.LowestVehiclePrice;
import com.test.allotment.service.VehicleAllotmentService;
import com.test.branch.Branch;
import com.test.customer.Customer;
import com.test.vehicle.Vehicle;
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

public class LowestVehiclePriceTestCase {

    List<Branch> branchList;
    List<Vehicle> vehicleList;
    Branch branch;
    List<VehicleAllotment> vehicleAllotments;
    LowestVehiclePrice lowestVehiclePrice;

    @BeforeEach
    void setup(){
        branchList = new ArrayList<>();
        vehicleList = new ArrayList<>();
        vehicleAllotments = new ArrayList<>();

        lowestVehiclePrice = new LowestVehiclePrice();

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
        vehiclesBooked.put(VehicleType.CAR, 0);
        vehiclesBooked.put(VehicleType.BIKE, 0);

        branch.setVehicles(vehicleList);
        branch.setVehiclesBooked(vehiclesBooked);
        branchList.add(branch);
    }

    @AfterEach
    void clear(){

    }

    @Test
    @DisplayName("Fetch Bike Available with lowest price")
    void testBikeWithLowestFare(){

        Vehicle vehicle = lowestVehiclePrice.rentVehicle(vehicleList, 5,6,VehicleType.BIKE,
                vehicleAllotments);
        assertEquals(vehicle.getBasePrice(), 30);

    }

    @Test
    @DisplayName("Fetch Car Available with lowest price")
    void testCarWithLowestFare(){

        Vehicle vehicle = lowestVehiclePrice.rentVehicle(vehicleList, 5,6,VehicleType.CAR,
                vehicleAllotments);
        assertEquals(vehicle.getBasePrice(), 100);

    }

    @Test
    @DisplayName("Fetch Bike Available with lowest price when already booking made")
    void testBikeWithLowestFareWhenAlreadyBookingMade(){

        Vehicle v = new Vehicle(1, "BR3453", "PULSAR", VehicleType.BIKE, 20);
        Customer customer = new Customer("test", 1234567890, "test@gmail.com");
        VehicleAllotment vehicleAllotment = VehicleAllotmentService.bookVehicle(v, 5, 6, customer);
        vehicleAllotments.add(vehicleAllotment);
        Vehicle vehicle = lowestVehiclePrice.rentVehicle(vehicleList, 5,6,VehicleType.BIKE,
                vehicleAllotments);
        assertEquals(vehicle.getBasePrice(), 30);

    }


    @Test
    @DisplayName("Fetch Car Available with lowest price when already booking made")
    void testCarWithLowestFareWhenAlreadyBookingMade(){

        Vehicle v = new Vehicle(1, "BR3453", "SUV", VehicleType.CAR, 80);
        Customer customer = new Customer("test", 1234567890, "test@gmail.com");
        VehicleAllotment vehicleAllotment = VehicleAllotmentService.bookVehicle(v, 5, 6, customer);
        vehicleAllotments.add(vehicleAllotment);
        Vehicle vehicle = lowestVehiclePrice.rentVehicle(vehicleList, 5,6,VehicleType.CAR,
                vehicleAllotments);
        assertEquals(vehicle.getBasePrice(), 100);

    }


}

