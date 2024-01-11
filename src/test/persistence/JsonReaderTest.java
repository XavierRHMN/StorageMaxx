package test.persistence;

import main.model.Vehicle;
import main.model.VehicleStorage;
import main.persistence.JsonReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            VehicleStorage vs = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNoVehicles() {
        JsonReader reader = new JsonReader("./data/testReaderNoVehicles.json");
        try {
            VehicleStorage vs = reader.read();
            assertEquals("My Vehicle Storage", vs.getName());
            assertEquals(0, vs.getNumVehicles());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderMultipleVehicles() {
        try {
            JsonReader reader = new JsonReader("./data/testWriterMultipleVehicles.json");
            VehicleStorage vs = reader.read();
            assertEquals("My Vehicle Storage", vs.getName());
            List<Vehicle> vehicles = vs.getVehicles();
            assertEquals(3, vehicles.size());
            checkVehicle("Dodge", "Challenger SRT", 2020, 100000, "Car", vehicles.get(0));
            checkVehicle("Lamborghini", "Aventador", 2022, 393695, "Car", vehicles.get(1));
            checkVehicle("BMW", "S1000 RR", 2023, 16995, "Car", vehicles.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}