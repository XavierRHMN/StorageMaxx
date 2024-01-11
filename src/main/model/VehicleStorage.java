package main.model;

import org.json.JSONArray;
import org.json.JSONObject;
import main.persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a vehicle storage having a collection of cars, bikes, etc
public class VehicleStorage implements Writable {
    private final String name;
    private List<Vehicle> vehicles;

    // EFFECTS: constructs an empty vehicle storage with a name
    public VehicleStorage(String name) {
        this.name = name;
        this.vehicles = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a vehicle to the vehicle list
    public void addVehicle(Vehicle vehicle, Boolean log) {
        if (log) {
            EventLog.getInstance().logEvent(new Event("Added " + vehicle.getTypeName()
                    +  " to garage "));
        }
        vehicles.add(vehicle);
    }

    // MODIFIES: this
    // EFFECTS: removes a vehicle from the vehicle list
    public void removeVehicle(Vehicle vehicle) {
        EventLog.getInstance().logEvent(new Event("Removed vehicle from garage "));
        vehicles.remove(vehicle);
    }

    // EFFECTS: returns a vehicle from the integer specified
    public Vehicle getVehicle(int i) {
        return vehicles.get(i);
    }

    // EFFECTS: returns the size of the vehicle list
    public int getNumVehicles() {
        return vehicles.size();
    }

    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("vehicles", vehiclesToJson());
        return json;
    }

    // EFFECTS: returns vehicles in this Vehicle Storage as a JSON array
    private JSONArray vehiclesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Vehicle v: vehicles) {
            jsonArray.put(v.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: gets an unmodifiable list of vehicles from the Vehicle Storage
    public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(vehicles);
    }

    // EFFECTS: gets the name of the Vehicle Storage
    public String getName() {
        return name;
    }
}

