package ui;

import javax.swing.*;

// Runs the vehicle manager application
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VehicleManagerGUI manager = new VehicleManagerGUI();
            manager.setSize(600, 800);
            manager.setLocation(650, 20);
            manager.setTitle("StorageMaxx");
            manager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            manager.setVisible(true); // Make the frame visible
        });

//        VehicleManager manager = new VehicleManager();
    }
}
