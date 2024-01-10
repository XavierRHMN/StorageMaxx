package ui;

import javax.swing.*;

// Runs the vehicle manager application
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VehicleManagerGUI manager = new VehicleManagerGUI();
            manager.setVisible(true); // Make the frame visible
            manager.setSize(500, 500);
            manager.setLocationRelativeTo(null);
            manager.setTitle("StorageMaxx");
            manager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });

//        new VehicleManager();
    }
}
