package ui;

import model.Event;
import model.EventLog;
import model.Vehicle;
import model.VehicleStorage;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

// Vehicle manager application that uses a GUI
public class VehicleManagerGUI extends JFrame {
    private VehicleStorage vehicleStorage;
    private EventLog eventLog;
    private JButton showAllButton;
    private JButton showCarsButton;
    private JButton showBikesButton;
    private JButton showYachtsButton;
    private JTextArea displayArea;
    private JTextField brandField;
    private JTextField nameField;
    private JTextField yearField;
    private JTextField priceField;
    private JTextField removeField;
    private JRadioButton carRadio;
    private JRadioButton bikeRadio;
    private JRadioButton yachtRadio;
    private JButton addButton;
    private JButton removeButton;
    private JButton saveButton;
    private JButton loadButton;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/Storage.json";

    // EFFECTS: runs the vehicle manager application
    public VehicleManagerGUI() {
        eventLog = EventLog.getInstance();
        vehicleStorage = new VehicleStorage("Users Storage");
        initComponents();
    }

    private void printLoggedEvents() {
            for (Event event : eventLog) { // Iterate over the events in the log
                System.out.println(event.toString()); // Print each event (uses Event's toString method)
            }
    }

    // MODIFIES: this
    // EFFECTS: initializes the necessary components for the application
    public void initComponents() {
        initButtons();
        initRadioButtons();
        initTextFields();
        initVehicles();
        initJsonReaderAndWriter();
        initButtonGifs();

        addActionListeners();
        layoutComponents();
    }

    // MODIFIES: this
    //EFFECTS: initializes the buttons
    private void initButtons() {
        showAllButton = new JButton("Show All Vehicles");
        showCarsButton = new JButton("Show Cars");
        showBikesButton = new JButton("Show Bikes");
        showYachtsButton = new JButton("Show Yachts");

        addButton = new JButton("Add Vehicle");
        removeButton = new JButton("Remove Vehicle");
        saveButton = new JButton("Save Garage");
        loadButton = new JButton("Load Garage");
    }

    // MODIFIES: this
    // EFFECTS: adds gifs to each button
    private void initButtonGifs() {
        ImageIcon carIcon = new ImageIcon("car.gif");
        ImageIcon starIcon = new ImageIcon("star.gif");
        ImageIcon bikeIcon = new ImageIcon("bike.gif");
        ImageIcon yachtIcon = new ImageIcon("yacht.gif");
        ImageIcon saveIcon = new ImageIcon("save.gif");


        Image resizedCarImage =  carIcon.getImage().getScaledInstance(40, 20, Image.SCALE_DEFAULT);
        Image resizedBikeImage = bikeIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        Image resizedStarImage = starIcon.getImage().getScaledInstance(20, 20, Image.SCALE_REPLICATE);
        Image resizedYachtImage = yachtIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        Image resizedSaveImage = saveIcon.getImage().getScaledInstance(20, 20, Image.SCALE_REPLICATE);



        addButton.setIcon(new ImageIcon(resizedStarImage));
        showCarsButton.setIcon(new ImageIcon(resizedCarImage));
        showBikesButton.setIcon(new ImageIcon(resizedBikeImage));
        showYachtsButton.setIcon(new ImageIcon(resizedYachtImage));
        //saveButton.setIcon(new ImageIcon(resizedSaveImage));


    }

    // MODIFIES: this
    //EFFECTS: initializes the radio buttons
    private void initRadioButtons() {
        carRadio = new JRadioButton("Cars");
        bikeRadio = new JRadioButton("Bikes");
        yachtRadio = new JRadioButton("Yachts");

        ButtonGroup group = new ButtonGroup();
        group.add(carRadio);
        group.add(bikeRadio);
        group.add(yachtRadio);
        carRadio.setSelected(true);
    }

    // MODIFIES: this
    //EFFECTS: initializes the text fields
    private void initTextFields() {
        displayArea = new JTextArea(20, 40);
        brandField = new JTextField();
        nameField = new JTextField();
        yearField = new JTextField();
        priceField = new JTextField();
        removeField = new JTextField("Enter Index of Vehicle to Remove");
    }

    // MODIFIES: this
    // EFFECTS: initializes the vehicles
    private void initVehicles() {
        Vehicle v1 = new Vehicle("Mazda", "MX-5", 1999, 5000, 1);
        Vehicle v2 = new Vehicle("Nissan", "370z", 2010, 16000, 1);
        Vehicle v3 = new Vehicle("Kawasaki", "Ninja ZX-10R", 2004, 16399, 2);
        Vehicle v4 = new Vehicle("Bayliner", "Capri", 2005, 12500, 3);
        vehicleStorage.addVehicle(v1, false);
        vehicleStorage.addVehicle(v2, false);
        vehicleStorage.addVehicle(v3, false);
        vehicleStorage.addVehicle(v4, false);
    }

    //EFFECTS: initializes the JSON Reader and Writer
    private void initJsonReaderAndWriter() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // REQUIRES: fields are filled in and type is selected
    // MODIFIES: this
    // EFFECTS: adds the vehicle to the garage
    private void addVehicle() {
        String brand = brandField.getText();
        String name = nameField.getText();
        int year = Integer.parseInt(yearField.getText());
        int price = Integer.parseInt(priceField.getText());

        if (carRadio.isSelected()) {
            Vehicle car = new Vehicle(brand, name, year, price, 1);
            vehicleStorage.addVehicle(car, true);
        } else if (bikeRadio.isSelected()) {
            Vehicle bike = new Vehicle(brand, name, year, price, 2);
            vehicleStorage.addVehicle(bike, true);
        } else if (yachtRadio.isSelected()) {
            Vehicle yacht = new Vehicle(brand, name, year, price, 3);
            vehicleStorage.addVehicle(yacht, true);
        }
        refreshDisplay();
    }

    // REQUIRES: remove vehicle by index field is filled in
    // MODIFIES this
    // EFFECTS: removes the vehicle from the garage
    private void removeVehicle() {
        Vehicle chosenVehicleToRemove = vehicleStorage.getVehicle(Integer.parseInt(removeField.getText()) - 1);
        vehicleStorage.removeVehicle(chosenVehicleToRemove);
//        System.out.println("The " + chosenVehicleToRemove.getBrand() + " " + chosenVehicleToRemove.getName()
//                + " has been successfully removed from the garage");
        refreshDisplay();
    }

    // EFFECTS: displays the current vehicles in garage
    private void refreshDisplay() {
        displayArea.setText(getSpecifiedVehicleInfo("Vehicle"));
    }

    // MODIFIES: this
    // EFFECTS: listens for users input and does the corresponding operation
    private void addActionListeners() {
        showAllButton.addActionListener(e -> displayArea.setText(getSpecifiedVehicleInfo("Vehicle")));
        showCarsButton.addActionListener(e -> displayArea.setText(getSpecifiedVehicleInfo("Car")));
        showBikesButton.addActionListener(e -> displayArea.setText(getSpecifiedVehicleInfo("Bike")));
        showYachtsButton.addActionListener(e -> displayArea.setText(getSpecifiedVehicleInfo("Yacht")));
        addButton.addActionListener(e -> addVehicle());
        removeButton.addActionListener(e -> removeVehicle());
        saveButton.addActionListener(e -> saveGarage());
        loadButton.addActionListener(e -> loadGarage());
    }

    // MODIFIES: this
    // EFFECTS: lays out the components of the user interface
    private void layoutComponents() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // laid out left side centered vertically
        gbc.fill = GridBagConstraints.HORIZONTAL; // Allow horizontal expansion
        gbc.insets = new Insets(5, 5, 5, 5);

        addBrandComponents(panel, gbc);
        addNameComponents(panel, gbc);
        addYearComponents(panel, gbc);
        addPriceComponents(panel, gbc);
        addRemoveComponents(panel,gbc);
        addRadioButtons(panel, gbc);
        addButtons(panel, gbc);
        addDisplayArea(panel, gbc);

        frame.add(panel);
        frame.setSize(600, 800);
        frame.setLocation(650, 20);
        frame.setTitle("StorageMaxx");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        addWindowLister(frame);
    }

    private void addWindowLister(JFrame frame) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLoggedEvents();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: lays out the brand label and field
    private void addBrandComponents(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel brandLabel = new JLabel("Brand:");
        panel.add(brandLabel, gbc);

        gbc.gridx++;
        panel.add(brandField, gbc);
    }

    // MODIFIES: this
    // EFFECTS: lays out the name label and field
    private void addNameComponents(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 1;

        JLabel nameLabel = new JLabel("Name:");
        panel.add(nameLabel, gbc);

        gbc.gridx++;
        panel.add(nameField, gbc);
    }

    // MODIFIES: this
    // EFFECTS: lays out the year label and field
    private void addYearComponents(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 2;

        JLabel yearLabel = new JLabel("Year:");
        panel.add(yearLabel, gbc);

        gbc.gridx++;
        panel.add(yearField, gbc);
    }

    // MODIFIES: this
    // EFFECTS: lays out the price label and field
    private void addPriceComponents(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 3;

        JLabel priceLabel = new JLabel("Price:");
        panel.add(priceLabel, gbc);

        gbc.gridx++;
        panel.add(priceField, gbc);
    }

    // MODIFIES: this
    // EFFECTS: lays out the remove vehicle by index label and field
    private void addRemoveComponents(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 4;

        JLabel removeLabel = new JLabel("Remove:");
        panel.add(removeLabel, gbc);

        gbc.gridx++;
        panel.add(removeField, gbc);

    }

    // MODIFIES: this
    // EFFECTS: lays out the radio buttons for the types of vehicles
    private void addRadioButtons(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(carRadio, gbc);

        gbc.gridy = 6;
        panel.add(bikeRadio, gbc);

        gbc.gridy = 7;
        panel.add(yachtRadio, gbc);
    }



    // MODIFIES: this
    // EFFECTS: lays out the add, remove, show car/bike/yacht, save, load buttons
    private void addButtons(JPanel panel, GridBagConstraints gbc) {
        gbc.gridwidth = 2;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(addButton, gbc);

        gbc.gridy = 9;
        panel.add(removeButton, gbc);

        gbc.gridy = 10;
        panel.add(showAllButton, gbc);

        gbc.gridy = 11;
        panel.add(showCarsButton, gbc);

        gbc.gridy = 12;
        panel.add(showBikesButton, gbc);

        gbc.gridy = 13;
        panel.add(showYachtsButton, gbc);

        gbc.gridy = 14;
        panel.add(saveButton, gbc);

        gbc.gridy = 15;
        panel.add(loadButton, gbc);
    }

    // MODIFIES: this
    // EFFECTS: lays out a scrollable area to display the vehicles in garage
    private void addDisplayArea(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 16;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // occupies all cells in remaining row
        gbc.fill = GridBagConstraints.BOTH; // resizes the display pane in both directions
        gbc.weightx = 1.0; // occupies any extra space when container is resized;
        gbc.weighty = 1.0; //
        panel.add(new JScrollPane(displayArea), gbc);
    }

    // REQUIRES: a valid type
    // EFFECTS: returns a string containing the brand, name, year, price of the vehicles in the garage
    // if the type is of 'Vehicle' then it will append the current vehicle's type to the string
    public String getSpecifiedVehicleInfo(String type) {
        StringBuilder info = new StringBuilder();
        int index = 1;
        info.append(type + "s:").append("\n");

        for (Vehicle vehicle : vehicleStorage.getVehicles()) {
            if (vehicle.getTypeName().equals(type) | type.equals("Vehicle")) {
                info.append(index + ")" + "   ");
                info.append("Brand: ").append(vehicle.getBrand()).append("   |   ");
                info.append("Name: ").append(vehicle.getName()).append("   |   ");
                info.append("Year: ").append(vehicle.getYear()).append("   |   ");
                info.append("Price: ").append(vehicle.getPrice());
                if (type.equals("Vehicle")) {
                    info.append("   |   ").append("Type: ").append(vehicle.getTypeName());
                }
                info.append("\n");
                index++;
            }
        }

        return info.toString();
    }

    // EFFECTS: saves the garage to file
    private void saveGarage() {
        try {
            jsonWriter.open();
            jsonWriter.write(vehicleStorage);
            jsonWriter.close();
            System.out.println("Saved " + vehicleStorage.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads the garage from file:
    private void loadGarage() {
        try {
            vehicleStorage = jsonReader.read();
            System.out.println("Loaded " + vehicleStorage.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}