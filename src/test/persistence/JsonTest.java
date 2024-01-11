package test.persistence;

import main.model.Vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkVehicle(String brand, String name, int year, int price, String type, Vehicle vehicle) {
        assertEquals(brand, vehicle.getBrand());
        assertEquals(name, vehicle.getName());
        assertEquals(year, vehicle.getYear());
        assertEquals(price, vehicle.getPrice());
        assertEquals(type, vehicle.getTypeName());
    }
}
