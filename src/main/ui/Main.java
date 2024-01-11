package main.ui;

import javax.swing.*;

// Runs the vehicle manager application
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(VehicleManagerGUI::new);

//        new VehicleManager();
    }
}
