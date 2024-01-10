package persistence;

import model.Vehicle;
import model.VehicleStorage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads a vehicle storage from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public VehicleStorage read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseVehicles(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses vehicles from JSON object and returns it
    private VehicleStorage parseVehicles(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        VehicleStorage vl = new VehicleStorage(name);
        addVehicles(vl, jsonObject);
        return vl;
    }

    // MODIFIES: vs
    // EFFECTS: parses vehicles from JSON object and adds them to vehicle storage
    private void addVehicles(VehicleStorage vs, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("vehicles");
        for (Object json : jsonArray) {
            JSONObject nextVehicle = (JSONObject) json;
            addVehicle(vs, nextVehicle);
        }
    }

    // MODIFIES: vs
    // EFFECTS: parses vehicle from JSON object and adds it to the vehicle storage
    private void addVehicle(VehicleStorage vs, JSONObject jsonObject) {
        String brand = jsonObject.getString("brand");
        String name = jsonObject.getString("name");
        int year = jsonObject.getInt("year");
        int price = jsonObject.getInt("price");
        Vehicle.VehicleType type = Vehicle.VehicleType.valueOf(jsonObject.getString("type"));
        Vehicle v = new Vehicle(brand, name, year, price, type);
        vs.addVehicle(v, false);
    }
}

