package ui;

import javax.swing.*;
import model.EventLog;

// Runs the vehicle manager application
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VehicleManagerGUI manager = new VehicleManagerGUI();
        });

//        VehicleManager manager = new VehicleManager();
    }
}
