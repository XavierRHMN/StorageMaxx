package model;

import org.json.JSONObject;
import persistence.Writable;

import static model.Vehicle.VehicleType.*;

// Represents a bought vehicle having a brand, name, year, price (in dollars), type
public class Vehicle implements Writable {
    private final String brand;
    private final String name;
    private final int year;
    private final int price;
    private final VehicleType type;

    public enum VehicleType {
        ALL, CAR, BIKE, YACHT, OTHER
    }

    // REQUIRES: name has a non-zero length, year <= current year, and price
    // must be a non-negative integer
    // EFFECTS: constructs a vehicle with a brand, name, year, price and type
    public Vehicle(String brand, String name, int year, int price, VehicleType type) {
        this.brand = brand;
        this.name = name;
        this.year = year;
        this.price = price;
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getPrice() {
        return price;
    }

    public VehicleType getType() {
        return type;
    }

    public String getTypeString() {
        return type.toString();
    }


    public String getTypeName() {
        if (getType() == CAR) {
            return "Car";
        } else if (getType() == BIKE) {
            return "Bike";
        } else if (getType() == YACHT) {
            return "Yacht";
        } else if (getType() == OTHER) {
            return "Other";
        }else {
            return "Vehicle";
        }
    }

    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("brand", brand);
        json.put("name", name);
        json.put("year", year);
        json.put("price", price);
        json.put("type", type.toString());
        return json;
    }
}


