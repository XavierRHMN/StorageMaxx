package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a bought vehicle having a brand, name, year, price (in dollars), type
public class Vehicle implements Writable {
    private String brand;
    private String name;
    private int year;
    private int price;
    private int type;


    // REQUIRES: name has a non-zero length, year <= current year, and price
    // must be a non-negative integer
    // EFFECTS: constructs a vehicle with a brand, name, year, price and type
    public Vehicle(String brand, String name, int year, int price, int type) {
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

    public int getType() {
        return type;
    }

    public String getTypeName() {
        if (getType() == 1) {
            return "Car";
        } else if (getType() == 2) {
            return "Bike";
        } else if (getType() == 3) {
            return "Yacht";
        } else {
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
        json.put("type", type);
        return json;
    }
}


