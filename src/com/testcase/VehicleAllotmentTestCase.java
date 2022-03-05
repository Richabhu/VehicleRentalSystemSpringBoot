package com.testcase;

import com.test.allotment.entity.VehicleAllotment;
import com.test.allotment.service.LowestVehiclePrice;
import com.test.allotment.service.VehicleAllotmentService;
import com.test.branch.Branch;
import com.test.branch.BranchService;
import com.test.customer.Customer;
import com.test.vehicle.Vehicle;
import com.test.vehicle.enums.VehicleType;
import javafx.util.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class VehicleAllotmentTestCase {

    List<Branch> branchList;
    List<Vehicle> vehicleList ;
    Branch branch;
    List<VehicleAllotment> vehicleAllotments ;

    LowestVehiclePrice lowestVehiclePrice;

    @BeforeEach
    void setup(){

        lowestVehiclePrice = new LowestVehiclePrice();
        vehicleAllotments = new ArrayList<>();
        vehicleList = new ArrayList<>();
        branchList = new ArrayList<>();

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
    @DisplayName("Check Vehicle Available")
    public void testVehicleAvailability() {
        boolean vehicleAvailable = VehicleAllotmentService.checkVehicleAvailable(vehicleAllotments, vehicleList.get(0),
                5, 6);
        assertEquals(true, true);
    }


    @Test
    @DisplayName("Check Vehicle is unavailable")
    public void testVehicleUnavailability() {

        Vehicle v = new Vehicle(1, "BR3453", "PULSAR", VehicleType.BIKE, 45);

        Customer customer = new Customer("test", 1234567890, "test@gmail.com");
        VehicleAllotment vehicleAllotment = new VehicleAllotment(v.getVehicleId(), 5,
                6, new Date(), customer);
        vehicleAllotments.add(vehicleAllotment);

        boolean vehicleAvailable = VehicleAllotmentService.checkVehicleAvailable(vehicleAllotments, v,
                5, 6);
        assertEquals(false, false);
    }

    @Test
    @DisplayName("Book Vehicle")
    public void testVehicleBooking(){

        Vehicle v = new Vehicle(1, "BR3453", "PULSAR", VehicleType.BIKE, 45);
        Customer customer = new Customer("test", 1234567890, "test@gmail.com");
        VehicleAllotment vehicleAllotment = VehicleAllotmentService.bookVehicle(v, 5, 6, customer);
        assertNotEquals(vehicleAllotment, null);
        assertEquals(vehicleAllotment.getVehicleId(), v.getVehicleId());
    }

    @Test
    @DisplayName("Fetch Alloted time for vehicle when vehicle is free")
    public void testVehicleBusyTimeForFreeVehicle(){
        Vehicle v = new Vehicle(1, "BR3453", "PULSAR", VehicleType.BIKE, 45);
        List<Pair<Integer, Integer>> busyTime= VehicleAllotmentService.getVehicleAllotment(vehicleAllotments, v);
        assertEquals(busyTime.size(), 0);

    }

    @Test
    @DisplayName("Fetch Alloted time for already assigned vehicle")
    public void testVehicleBusyTimeForBusyVehicle(){
        Vehicle v = new Vehicle(1, "BR3453", "PULSAR", VehicleType.BIKE, 45);

        Customer customer = new Customer("test", 1234567890, "test@gmail.com");
        VehicleAllotment vehicleAllotment = new VehicleAllotment(v.getVehicleId(), 5,
                6, new Date(), customer);
        vehicleAllotments.add(vehicleAllotment);

        List<Pair<Integer, Integer>> busyTime= VehicleAllotmentService.getVehicleAllotment(vehicleAllotments, v);


        assertEquals(busyTime.get(0).getKey(), 5);
        assertEquals(busyTime.get(0).getValue(), 6);

    }


    @Test
    @DisplayName("Fetch Free time of vehicle")
    public void testFetchingVehicleFreeTime(){

        Vehicle v = new Vehicle(1, "BR3453", "PULSAR", VehicleType.BIKE, 45);

        Customer customer = new Customer("test", 1234567890, "test@gmail.com");
        VehicleAllotment vehicleAllotment = new VehicleAllotment(v.getVehicleId(), 5,
                6, new Date(), customer);
        vehicleAllotments.add(vehicleAllotment);

        List<Pair<Integer, Integer>> freeTime= VehicleAllotmentService.getVehicleAvailability(vehicleAllotments, v);
        assertNotEquals(freeTime.size(), 0);
        assertEquals(freeTime.get(0).getKey(), 0);
        assertEquals(freeTime.get(0).getValue(), 5);
        assertEquals(freeTime.get(1).getKey(), 5);
        assertEquals(freeTime.get(1).getValue(), 24);

    }



}
