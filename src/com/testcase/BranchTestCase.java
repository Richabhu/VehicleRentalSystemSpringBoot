package com.testcase;

import static org. junit.Assert.*;

import com.test.branch.Branch;
import com.test.branch.BranchService;
import com.test.vehicle.enums.VehicleType;
//import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchTestCase {

    List<Branch> branchList ;
    Branch branch;

    @BeforeEach
    void setup(){

        branchList = new ArrayList<>();
        Map<VehicleType, Integer> vehiclesAllowed = new HashMap<>();
        vehiclesAllowed.put(VehicleType.CAR, 1);
        vehiclesAllowed.put(VehicleType.BIKE, 1);
        branch= new Branch("KORMANGALA",  "BANGALORE", vehiclesAllowed);

        branchList.add(branch);
    }

    @AfterEach
    void clear(){
//        branchList = new ArrayList<>();
    }

    @Test
    @DisplayName("Branch is valid.")
    public void testValidBranch() {
        Branch b = BranchService.validBranch(branchList, "KORMANGALA");
        assertEquals(b.getBranchName(), "KORMANGALA",
                "Branch is valid.");
    }

    @Test
    @DisplayName("Branch is invalid.")
    public void testInValidBranch() {
        Branch b = BranchService.validBranch(branchList, "KORMANGALATEST");
        assertEquals(b, null,
                "Branch is invalid.");
    }

    @Test
    @DisplayName("Fetch branch on ID.")
    public void testBranchFetchingOnId() {
        Branch b = BranchService.fetchBranchOnId(branchList, 1);
        assertEquals(b.getBranchName(), "KORMANGALA",
                "Branch is valid");
    }


    @Test
    @DisplayName("No Surge Case")
    public void testNoSurge(){

        Map<VehicleType, Integer> vehiclesAllowed = new HashMap<>();
        vehiclesAllowed.put(VehicleType.CAR, 10);
        vehiclesAllowed.put(VehicleType.BIKE, 1);
        branch.setVehiclesAllowed(vehiclesAllowed);

        Map<VehicleType, Integer> vehiclesBooked = new HashMap<>();
        vehiclesBooked.put(VehicleType.CAR, 8);
        vehiclesBooked.put(VehicleType.BIKE, 0);
        branch.setVehiclesBooked(vehiclesBooked);

        double surge = BranchService.calculateSurge(branch, 10, VehicleType.BIKE, 10);
        assertEquals(surge, 0.0, "No surge applied");
    }

    @Test
    @DisplayName("Surge Case")
    public void testSurge(){

        Map<VehicleType, Integer> vehiclesAllowed = new HashMap<>();
        vehiclesAllowed.put(VehicleType.CAR, 10);
        vehiclesAllowed.put(VehicleType.BIKE, 1);
        branch.setVehiclesAllowed(vehiclesAllowed);

        Map<VehicleType, Integer> vehiclesBooked = new HashMap<>();
        vehiclesBooked.put(VehicleType.CAR, 8);
        vehiclesBooked.put(VehicleType.BIKE, 0);
        branch.setVehiclesBooked(vehiclesBooked);

        double surge = BranchService.calculateSurge(branch, 100, VehicleType.CAR, 10);
        assertEquals(surge, 10.0, " surge applied");
    }







}
