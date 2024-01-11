package test.model;

import main.model.Vehicle;
import main.model.VehicleStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static main.model.Vehicle.VehicleType.BIKE;
import static main.model.Vehicle.VehicleType.CAR;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleStorageTest {
    private VehicleStorage vehicleStorage;
    private Vehicle v1;
    private Vehicle v2;
    private Vehicle v3;
    private Vehicle v4;

    @BeforeEach
    void runBefore() {
        vehicleStorage = new VehicleStorage("Test Storage");
    }

    @Test
    void testGetVehicle() {
        vehicleStorage.addVehicle(v1, false);
        vehicleStorage.addVehicle(v2, false);
        assertEquals(v2, vehicleStorage.getVehicle(1));
    }

    @Test
    void testAddSingleVehicle() {
        vehicleStorage.addVehicle(v1, false);
        assertEquals(1, vehicleStorage.getNumVehicles());
    }

    @Test
    void testAddMultipleVehicles() {
        vehicleStorage.addVehicle(v1, false);
        vehicleStorage.addVehicle(v2, false);
        vehicleStorage.addVehicle(v3, false);
        assertEquals(3, vehicleStorage.getNumVehicles());
    }

    @Test
    void testAddRemoveSingleVehicle() {
        v1 = new Vehicle("Dodge", "Demon SRT", 2020, 100000, CAR);
        vehicleStorage.addVehicle(v1, false);
        vehicleStorage.removeVehicle(v1);
        assertEquals(0, vehicleStorage.getNumVehicles());
    }

    @Test
    void testAddRemoveMultipleVehicles() {
        v1 = new Vehicle("Dodge", "Demon SRT", 2020, 100000, CAR);
        v3 = new Vehicle("Kawasaki", "Ninja ZX-10R", 2004, 16399, BIKE);
        vehicleStorage.addVehicle(v1, false);
        vehicleStorage.addVehicle(v3, false);
        vehicleStorage.removeVehicle(v1);
        vehicleStorage.removeVehicle(v3);
        assertEquals(0, vehicleStorage.getNumVehicles());
    }
}