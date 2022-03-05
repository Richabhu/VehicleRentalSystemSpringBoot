package com.test;

import com.sun.org.apache.xml.internal.utils.SystemIDResolver;
import com.test.allotment.entity.VehicleAllotment;
import com.test.allotment.service.LowestVehiclePrice;
import com.test.allotment.service.VehicleAllotmentService;
import com.test.branch.Branch;
import com.test.branch.BranchService;
import com.test.customer.Customer;
import com.test.vehicle.Vehicle;
import com.test.vehicle.VehicleService;
import com.test.vehicle.enums.VehicleType;

import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("HEllo World");


        /**
         *
         * addBranch KORMANGALA BANGALORE CAR 5 BIKE 0
         * CAR SUV BR123 100
         * CAR SEDAN BR234 100
         * CAR SUV2 BR1233 100
         * CAR SUV3 BR1234 100
         * CAR SUV4 BR1236 100
         * BIKE BULLET BR456 30
         * BIKE PULSAR BR345 45
         *
         * addVehicle KORMANGALA BIKE BULLET BR453 30
         *
         * rent CAR 5 6
         *
         * rent BIKE 7 8
         *
         * RICHA richabhuwania5@gmail.com 888888
         *
         *
         * display KORMANGALA
         *
         * drop CAR BR123 KORMANGALA
         * drop BIKE BR456 KORMANGALA
         */

        Scanner sc = new Scanner(System.in);


        List<Branch> branchList = new ArrayList<>();
        List<Vehicle> vehicleList = new ArrayList<>();
        List<Customer> customerList = new ArrayList<>();
        List<VehicleAllotment> vehicleAllotments = new ArrayList<>();

        while (true){
            System.out.println("Enter commands");
            String inputs  = sc.nextLine();
            String input[] = inputs.split(" ");

            String cmd = input[0];

            if(cmd.equals("addBranch")){
                // todo: add branch
                String branchName = input[1];
                String city = input[2];
                Map<VehicleType, Integer> vehicleAllowed = new HashMap<>();

                if(String.valueOf(input[3]).equals("CAR"))
                    vehicleAllowed.put(VehicleType.CAR, Integer.parseInt(input[4]));
                if(String.valueOf(input[5]).equals("BIKE"))
                    vehicleAllowed.put(VehicleType.BIKE, Integer.parseInt(input[6]));

                Branch b = new Branch(branchName, city, vehicleAllowed);
                branchList.add(b);

                int branchId = b.getBranchId();

                List<Vehicle> vehicleList1 = new ArrayList<>();
                // take input for car
                for(int i = 0; i<Integer.parseInt(input[4]); i++){
                    // create car obj
                    String vehicleInputs = sc.nextLine();
                    String vehicleInput[] = vehicleInputs.split(" ");

                    String vehicleName = vehicleInput[1];
                    String vehicleNo = vehicleInput[2];
                    double fare = Double.parseDouble(vehicleInput[3]);
                    Vehicle v = new Vehicle(branchId, vehicleNo, vehicleName, VehicleType.CAR, fare);

                    vehicleList.add(v);
                    vehicleList1.add(v);

                }
                // take input for bike
                for(int i = 0; i<Integer.parseInt(input[6]); i++){
                    // create bike obj

                    String vehicleInputs = sc.nextLine();
                    String vehicleInput[] = vehicleInputs.split(" ");

                    String vehicleName = vehicleInput[1];
                    String vehicleNo = vehicleInput[2];
                    double fare = Double.parseDouble(vehicleInput[3]);
                    Vehicle v = new Vehicle(branchId, vehicleNo, vehicleName, VehicleType.BIKE, fare);

                    vehicleList.add(v);
                    vehicleList1.add(v);

                }
                b.setVehicles(vehicleList1);
                System.out.print("Branch added");

            }
            else if(cmd.equals("addVehicle")){
                // add vehicle to existing branch

                String branchName = input[1];
                Branch b = BranchService.validBranch(branchList, branchName);
                if(b != null){
                    //valid branch , insert vehicle

                    //  addVehicle KORMANGALA BIKE BULLET BR456 30
                    VehicleType type;
                    if(input[2].equals("BIKE")) {

                        type = VehicleType.BIKE;
                    }
                    else if(input[2].equals("CAR")) {
                        type = VehicleType.CAR;
                    }
                    else
                    {
                        System.out.println(input[2] + " is not a valid type");
                        continue;
                    }

                    String vehicleName = input[3];
                    String vehicleNo = input[4];
                    double fare = Double.parseDouble(input[5]);
                    if(VehicleService.vehicleExists(vehicleList, vehicleNo) != null)
                    {
                        System.out.println("Vehicle no " + vehicleNo +" already registered. ");
                        continue;
                    }
                    Vehicle v = new Vehicle(b.getBranchId(), vehicleNo, vehicleName, type, fare);
                    vehicleList.add(v);

                    // update in the earlier vehicle list
                    b.getVehicles().add(v);
                    b.getVehiclesAllowed().put(type, b.getVehiclesAllowed().get(type) + 1);
                    System.out.println("Vehicles added");

                }
                else{
                    System.out.println("Branch Name " + branchName +" is not registered with us.");
                }

            }
            else if(cmd.equals("rent")){
                //  rent vehicle

                VehicleType type;
                if(input[1].equals("BIKE")) {

                    type = VehicleType.BIKE;
                }
                else if(input[1].equals("CAR")) {
                    type = VehicleType.CAR;
                }
                else
                {
                    System.out.println(input[2] + " is not a valid type");
                    continue;
                }

                int startTime = Integer.parseInt(input[2]);
                int endTime = Integer.parseInt(input[3]);

                LowestVehiclePrice vehiclePrice = new LowestVehiclePrice();
                Vehicle v = vehiclePrice.rentVehicle(vehicleList, startTime, endTime,type, vehicleAllotments);
                if(v != null)
                {
                    // fetch customer details and do proper allotment
                    System.out.println("Vehicle selected is: "+ v.getVehicleName() + " on "+
                            v.getBasePrice() +" as base price.");
                    System.out.println("Please enter the customer details to book the vehicle.");
                    String customerInput = sc.nextLine();
                    String customer[] = customerInput.split(" ");

                    String name = customer[0];
                    String email = customer[1];
                    Long phone = Long.parseLong(customer[2]);

                    Customer customer1 = new Customer(name, phone, email);

                    VehicleAllotment allotment = VehicleAllotmentService.bookVehicle(v,startTime, endTime , customer1);

                    vehicleAllotments.add(allotment);
                    // update the branch

                    Branch b = BranchService.fetchBranchOnId(branchList, v.getBranchId());
                    //TODO: put Surge percentage on constants
                    double surgePrice = BranchService.calculateSurge(b, v.getBasePrice(), type, 10);
                    double totalFare = v.getBasePrice() + surgePrice;
                    customer1.setFare(totalFare);
                    // update the vehicle booking in branch
                    b.getVehiclesBooked().put(type, b.getVehiclesBooked().get(type) + 1);

                    System.out.println(" Vehicle "+ v.getVehicleName() + " is booked with " + surgePrice
                            + " as surge price. Total fare: " + totalFare +". Please collect from  "+ b.getBranchName());
                }
                else
                {
                    System.out.println("Sorry, there is no vehicle available any where in the city.");
                }
            }
            else if(cmd.equals("display")){
                // todo:display available vehicle
                String branchName = input[1];
                Branch b = BranchService.validBranch(branchList, branchName);
                if(b != null){
                    BranchService.displayVehicleOnBranch(b, vehicleList, vehicleAllotments);
                }
                else{
                    System.out.println("Please give the valid branch name");
                }

            }
            else if(cmd.equals("drop")){
                // todo:drop vehicle
                String vehicleNo = input[2];
                String branchName = input[3];

                VehicleType type;
                if(input[1].equals("BIKE")) {

                    type = VehicleType.BIKE;
                }
                else if(input[1].equals("CAR")) {
                    type = VehicleType.CAR;
                }
                else
                {
                    System.out.println(input[2] + " is not a valid type");
                    continue;
                }
                Branch b = BranchService.validBranch(branchList, branchName);
                if(b != null){

                    Vehicle v = VehicleService.vehicleExists(vehicleList, vehicleNo);
                    if(v!=null){
                        // proper vehicle
                        if(v.getBranchId() == b.getBranchId()){
                            // vehicle accepted
                            b.getVehiclesBooked().put(type, b.getVehiclesBooked().get(type) - 1);
                            // todo: update customer booking status
                            System.out.println("Vehicle accepted");

                        }
                        else{
                            System.out.println("Please submit the vehicle at "+ b.getBranchName() +" branch");
                        }

                    }
                    else{
                        System.out.println("Trying to submit unregistered vehicle.");
                    }
                }else {
                    System.out.println("Branch Name " + branchName +" is not registered with us.");
                }

            }
            else if(cmd.equals("EXIT") || cmd.equals("exit"))
                return;
            else
                System.out.println("Invalid input");
        }


    }
}
