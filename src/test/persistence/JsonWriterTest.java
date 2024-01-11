package test.persistence;

import main.model.Vehicle;
import main.model.VehicleStorage;
import main.persistence.JsonReader;
import main.persistence.JsonWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static main.model.Vehicle.VehicleType.CAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            VehicleStorage vs = new VehicleStorage("My Vehicle Storage");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNoVehicles() {
        try {
            VehicleStorage vs = new VehicleStorage("My Vehicle Storage");
            JsonWriter writer = new JsonWriter("./data/testWriterNoVehicles.json");
            writer.open();
            writer.write(vs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoVehicles.json");
            vs = reader.read();
            assertEquals("My Vehicle Storage", vs.getName());
            assertEquals(0, vs.getNumVehicles());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterMultipleVehicles() {
        try {
            VehicleStorage vs = new VehicleStorage("My Vehicle Storage");
            vs.addVehicle(new Vehicle("Dodge", "Challenger SRT", 2020, 100000, CAR), false);
            vs.addVehicle(new Vehicle("Lamborghini", "Aventador", 2022, 393695, CAR), false);
            vs.addVehicle(new Vehicle("BMW", "S1000 RR", 2023, 16995, CAR), false);
            JsonWriter writer = new JsonWriter("./data/testWriterMultipleVehicles.json");
            writer.open();
            writer.write(vs);
            writer.close();
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}