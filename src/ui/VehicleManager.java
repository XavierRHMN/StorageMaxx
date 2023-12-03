package ui;

import model.Vehicle;
import model.VehicleStorage;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Vehicle manager application that uses console commands
public class VehicleManager {
    private static final String JSON_STORE = "./data/Storage.json";
    private VehicleStorage vehicleStorage;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the vehicle manager application
    public VehicleManager() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runVM();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runVM() {
        vehicleStorage = new VehicleStorage("My Vehicle Storage");
        initVehicles();
        String command;

        while (true) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                break;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> all vehicles");
        System.out.println("\t1 -> cars");
        System.out.println("\t2 -> bikes");
        System.out.println("\t3 -> yachts");
        System.out.println("\ta -> add a vehicle");
        System.out.println("\tr -> remove a vehicle");
        System.out.println("\ts -> save vehicles to file");
        System.out.println("\tl -> load vehicles from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: initializes vehicles
    private void initVehicles() {
        Vehicle v1 = new Vehicle("Mazda", "MX-5", 1999, 5000, 1);
        Vehicle v2 = new Vehicle("Nissan", "370z", 2010, 16000, 1);
        Vehicle v3 = new Vehicle("Kawasaki", "Ninja ZX-10R", 2004, 16399, 2);
        Vehicle v4 = new Vehicle("Bayliner", "Capri", 2005, 12500, 3);
        vehicleStorage.addVehicle(v1, true);
        vehicleStorage.addVehicle(v2, true);
        vehicleStorage.addVehicle(v3, true);
        vehicleStorage.addVehicle(v4, true);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("v")) {
            displayAll();
        } else if (command.equals("1")) {
            displayCars();
        } else if (command.equals("2")) {
            displayBikes();
        } else if (command.equals("3")) {
            displayYachts();
        } else if (command.equals("a")) {
            addVehicleFunction();
        } else if (command.equals("r")) {
            removeVehicleFunction(input);
        } else if (command.equals("s")) {
            saveVehicles();
        } else if (command.equals("l")) {
            loadVehicles();
        } else {
            System.out.println("Invalid Selection");
        }
    }

    // EFFECTS: prints out a string for an empty garage
    private void printEmptyGarage(String s, int vehicleType) {
        VehicleStorage specifiedVehicles = new VehicleStorage("Specified Vehicles");
        for (Vehicle vehicle: vehicleStorage.getVehicles()) {
            if (vehicle.getType() == vehicleType || vehicleType == 0) {
                specifiedVehicles.addVehicle(vehicle, false);
            }
        }
        if (specifiedVehicles.getNumVehicles() == 0) {
            System.out.println("You have no " + s);
        }
    }

    // EFFECTS: displays all vehicles to user
    private void displayAll() {
        int all = 0;
        System.out.println("Vehicles:");
        displaySpecifiedVehicle(all);
        printEmptyGarage("vehicles!", all);
    }

    // EFFECTS: displays all cars to user
    private void displayCars() {
        int car = 1;
        System.out.println("Cars:");
        displaySpecifiedVehicle(car);
        printEmptyGarage("cars!", car);
    }

    // EFFECTS: displays all bikes to user
    private void displayBikes() {
        int bike = 2;
        System.out.println("Bikes:");
        displaySpecifiedVehicle(bike);
        printEmptyGarage("bikes!", bike);
    }

    // EFFECTS: displays all yachts to user
    private void displayYachts() {
        int yacht = 3;
        System.out.println("Yachts:");
        displaySpecifiedVehicle(yacht);
        printEmptyGarage("yachts!", yacht);
    }

    // EFFECTS: helper function that displays the specified vehicle
    private void displaySpecifiedVehicle(int type) {
        int count = 0;
        for (Vehicle vehicle : vehicleStorage.getVehicles()) {
            if (vehicle.getType() == type || type == 0) {
                count++;
                System.out.println("(" + count + ")" + " " + vehicle.getBrand() + " " + vehicle.getName()
                        + " Price: " + "$" + vehicle.getPrice() + " Year: " + vehicle.getYear());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a vehicle to the garage and displays a message
    // if done successfully
    private void addVehicleFunction() {
        String brand = vehicleBrand(input);
        String name = vehicleName(input);
        int year = vehicleYear(input);
        int price = vehiclePrice(input);
        int type = vehicleType(input);

        Vehicle vehicle = new Vehicle(brand, name, year, price, type);
        vehicleStorage.addVehicle(vehicle, false);
        System.out.println("Your" + " " + vehicle.getBrand() + " " + vehicle.getName()
                + " " + "has been successfully added to the garage");
    }

    //EFFECTS: if user inputs a string continue, otherwise
    // print invalid input and ask them to try again
    public String inputAString(Scanner input) {
        while (true) {
            if (input.hasNextInt()) {
                System.out.println("Invalid input. Please try again.");
                input.next();
            } else {
                return input.next();
            }
        }
    }

    //EFFECTS: if user inputs an integer continue, otherwise
    // print invalid input and ask them to try again
    public int inputAnInteger(Scanner input) {
        while (true) {
            if (!input.hasNextInt()) {
                System.out.println("Invalid input. Please try again.");
                input.next();
            } else {
                return input.nextInt();
            }
        }
    }

    // EFFECTS: user input changes the vehicle's type
    public int inputVehicleType(Scanner input) {
        while (true) {
            if (!input.hasNextInt()) {
                System.out.println("Invalid input. Please try again.");
                input.next();
            } else {
                int value = input.nextInt();
                if (value >= 1 && value <= vehicleStorage.getLargestType()) {
                    return value;
                } else {
                    System.out.println("Invalid input. Please enter a value between 1 and "
                            + vehicleStorage.getLargestType());
                }
            }
        }
    }

    // REQUIRES: a non empty string
    // EFFECTS: brand of the vehicle is the users input
    public String vehicleBrand(Scanner input) {
        System.out.print("Please enter the vehicle's brand: ");
        return inputAString(input);
    }

    // REQUIRES: a non empty string
    // EFFECTS: name of the vehicle is the users input
    public String vehicleName(Scanner input) {
        System.out.print("Please enter the vehicle's name: ");
        return inputAString(input);
    }

    // REQUIRES: at least one integer
    // EFFECTS: year of the vehicle is the users input
    public int vehicleYear(Scanner input) {
        System.out.print("Please enter the vehicle's year: ");
        return inputAnInteger(input);
    }

    // REQUIRES: at least one integer
    // EFFECTS: price of the vehicle is the users input
    public int vehiclePrice(Scanner input) {
        System.out.print("Please enter the vehicle's price: ");
        return inputAnInteger(input);
    }

    // EFFECTS: type of the vehicle is the users input
    public int vehicleType(Scanner input) {
        System.out.println("Type '1' to save as a car");
        System.out.println("Type '2' to save as a bike");
        System.out.println("Type '3' to save as a yacht");
        return inputVehicleType(input);
    }

    // MODIFIES: this
    // EFFECTS: removes the vehicle at the user inputted index, if
    // the input is an integer, then if 0 <= input < vehicles.size()
    // is true, remove the vehicle at that index or ask the user to enter
    // a valid index, otherwise if the input is not an integer and is 'q'
    // then quit, otherwise ask for a valid input.
    public void removeVehicleFunction(Scanner input) {
        System.out.println("Enter the index of the vehicle you wish to remove from the garage or press q to quit");

        int indexOfVehicleToRemove;

        displayAll();

        while (true) {
            if (input.hasNextInt()) {
                indexOfVehicleToRemove = input.nextInt();

                if (indexOfVehicleToRemove >= 1 && indexOfVehicleToRemove < vehicleStorage.getNumVehicles() + 1) {
                    removeVehicle(indexOfVehicleToRemove - 1);
                    break;
                } else {
                    System.out.println("Invalid index. Please enter a valid index between 0"
                            + " and smaller than the current garage size");
                }

            } else if (input.next().equals("q")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid integer");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a vehicles from the garage.
    public void removeVehicle(int indexOfVehicleToRemove) {
        Vehicle chosenVehicleToRemove = vehicleStorage.getVehicle(indexOfVehicleToRemove);
        vehicleStorage.removeVehicle(chosenVehicleToRemove);
        System.out.println("The " + chosenVehicleToRemove.getBrand() + " " + chosenVehicleToRemove.getName()
                + " has been successfully removed from the garage");
    }

    // EFFECTS: saves vehicles to file
    private void saveVehicles() {
        try {
            jsonWriter.open();
            jsonWriter.write(vehicleStorage);
            jsonWriter.close();
            System.out.println("Saved " + vehicleStorage.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads vehicles from file
    private void loadVehicles() {
        try {
            vehicleStorage = jsonReader.read();
            System.out.println("Loaded " + vehicleStorage.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}

