package test.model;

import main.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static main.model.Vehicle.VehicleType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleTest {
    private Vehicle v1;
    private Vehicle v2;
    private Vehicle v3;
    private Vehicle v4;
    private Vehicle v5;

    @BeforeEach
    public void runBefore() {
        v1 = new Vehicle("Dodge", "Demon SRT", 2020, 100000, CAR);
        v2 = new Vehicle("Nissan", "370z", 2010, 16000, CAR);
        v3 = new Vehicle("Kawasaki", "Ninja ZX-10R", 2004, 16399, BIKE);
        v4 = new Vehicle("Bayliner", "Capri", 2005, 12500, YACHT);
        v5 = new Vehicle("test", " ", 0, 0, OTHER);
    }

    @Test
    public void testConstructor() {
        assertEquals("Dodge", v1.getBrand());
        assertEquals("370z", v2.getName());
        assertEquals(100000, v1.getPrice());
        assertEquals(2005, v4.getYear());
        assertEquals(CAR, v1.getType());
    }

    @Test
    public void getTypeName() {
        assertEquals("Car", v1.getTypeName());
        assertEquals("Car", v2.getTypeName());
        assertEquals("Bike", v3.getTypeName());
        assertEquals("Yacht", v4.getTypeName());
        assertEquals("Other", v5.getTypeName());

    }
}