package main.ui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import main.model.Vehicle;
import main.model.VehicleStorage;
import main.persistence.JsonReader;
import main.persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import static main.model.Vehicle.VehicleType.*;

// Vehicle manager application that uses a GUI
public class VehicleManagerGUI extends JFrame {
    private VehicleStorage vehicleStorage;
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
    private JRadioButton otherRadio;
    private JButton addButton;
    private JButton removeButton;
    private JButton saveButton;
    private JButton loadButton;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JScrollPane scrollPane;
    private static final String JSON_STORE = "./data/Storage.json";

    // EFFECTS: runs the vehicle manager application
    public VehicleManagerGUI() {
        vehicleStorage = new VehicleStorage("Users Storage");
        setLookAndFeel();
        initComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes the necessary components for the application
    public void initComponents() {

        initDisplay();
        initButtons();
        initRadioButtons();
        initTextFields();
        initVehicles();
        initJsonReaderAndWriter();
        initButtonGifs();

        addActionListeners();
        layoutComponents();
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel (new FlatMacDarkLaf());
        } catch (Exception ex) {
            System.err.println( "Failed to initialize LaF");
        }
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
        otherRadio = new JRadioButton("Other");

        ButtonGroup group = new ButtonGroup();
        group.add(carRadio);
        group.add(bikeRadio);
        group.add(yachtRadio);
        group.add(otherRadio);
        carRadio.setSelected(true);
    }

    public void initDisplay() {
        // Initialize other components...
        displayArea = new JTextArea(20, 40);
        displayArea.setEditable(false); // If you don't want users to edit the text
        scrollPane = new JScrollPane(displayArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // Initialize other components...
    }

    // MODIFIES: this
    //EFFECTS: initializes the text fields
    private void initTextFields() {
        brandField = new JTextField();
        nameField = new JTextField();
        yearField = new JTextField();
        priceField = new JTextField();
        removeField = new JTextField("Enter Index of Vehicle to Remove");
    }

    // MODIFIES: this
    // EFFECTS: initializes the vehicles
    private void initVehicles() {
        Vehicle v1 = new Vehicle("Mazda", "MX-5", 1999, 5000, CAR);
        Vehicle v2 = new Vehicle("Nissan", "370z", 2010, 16000, CAR);
        Vehicle v3 = new Vehicle("Kawasaki", "Ninja ZX-10R", 2004, 16399, BIKE);
        Vehicle v4 = new Vehicle("Bayliner", "Capri", 2005, 12500, YACHT);
        vehicleStorage.addVehicle(v1, true);
        vehicleStorage.addVehicle(v2, true);
        vehicleStorage.addVehicle(v3, true);
        vehicleStorage.addVehicle(v4, true);
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
            Vehicle car = new Vehicle(brand, name, year, price, CAR);
            vehicleStorage.addVehicle(car, false);
        } else if (bikeRadio.isSelected()) {
            Vehicle bike = new Vehicle(brand, name, year, price, BIKE);
            vehicleStorage.addVehicle(bike, false);
        } else if (yachtRadio.isSelected()) {
            Vehicle yacht = new Vehicle(brand, name, year, price, YACHT);
            vehicleStorage.addVehicle(yacht, false);
        } else if (otherRadio.isSelected()) {
            Vehicle yacht = new Vehicle(brand, name, year, price, OTHER);
            vehicleStorage.addVehicle(yacht, false);
        }
        refreshDisplay();
    }

    // REQUIRES: remove vehicle by index field is filled in
    // MODIFIES this
    // EFFECTS: removes the vehicle from the garage
    private void removeVehicle() {
        Vehicle chosenVehicleToRemove = vehicleStorage.getVehicle(Integer.parseInt(removeField.getText()) - 1);
        vehicleStorage.removeVehicle(chosenVehicleToRemove);
        System.out.println("The " + chosenVehicleToRemove.getBrand() + " " + chosenVehicleToRemove.getName()
                + " has been successfully removed from the garage");
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
        showYachtsButton.addActionListener(e -> displayArea.setText(getSpecifiedVehicleInfo("Other")));
        addButton.addActionListener(e -> addVehicle());
        removeButton.addActionListener(e -> removeVehicle());
        saveButton.addActionListener(e -> saveGarage());
        loadButton.addActionListener(e -> loadGarage());
    }

    // MODIFIES: this
    // EFFECTS: lays out the components of the user interface
    private void layoutComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add tabs - don't add components to them here
        tabbedPane.addTab("Manage", createManagePanel());
        tabbedPane.addTab("All", new JPanel());
        tabbedPane.addTab("Cars", new JPanel());
        tabbedPane.addTab("Bikes", new JPanel());
        tabbedPane.addTab("Yachts", new JPanel());
        tabbedPane.addTab("Other", new JPanel());

        // Add tabbedPane to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        panel.add(tabbedPane, gbc);

        // Add ChangeListener to tabbedPane
        tabbedPane.addChangeListener(e -> {
            JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
            int index = sourceTabbedPane.getSelectedIndex();
            String tabTitle = sourceTabbedPane.getTitleAt(index);

            // Remove the scrollPane from its current parent (if any)
            Container parent = scrollPane.getParent();
            if (parent != null) {
                parent.remove(scrollPane);
            }

            if (!"Manage".equals(tabTitle)) {
                // Add the scrollPane to the current tab
                JPanel currentTab = (JPanel) tabbedPane.getComponentAt(index);
                currentTab.setLayout(new BorderLayout());
                currentTab.add(scrollPane, BorderLayout.CENTER);
                updateDisplayArea(tabTitle); // Update the display area based on the selected tab
            }

            panel.revalidate();
            panel.repaint();
        });

        add(panel);
    }


    private JPanel createManagePanel() {
        JPanel managePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Brand Field
        managePanel.add(new JLabel("Brand:"), gbc);
        gbc.gridx++;
        managePanel.add(brandField, gbc);

        // Name Field
        gbc.gridx = 0;
        gbc.gridy++;
        managePanel.add(new JLabel("Name:"), gbc);
        gbc.gridx++;
        managePanel.add(nameField, gbc);

        // Year Field
        gbc.gridx = 0;
        gbc.gridy++;
        managePanel.add(new JLabel("Year:"), gbc);
        gbc.gridx++;
        managePanel.add(yearField, gbc);

        // Price Field
        gbc.gridx = 0;
        gbc.gridy++;
        managePanel.add(new JLabel("Price:"), gbc);
        gbc.gridx++;
        managePanel.add(priceField, gbc);

        // Radio Buttons for Vehicle Type
        gbc.gridx = 0;
        gbc.gridy++;
        managePanel.add(carRadio, gbc);
        gbc.gridx++;
        managePanel.add(bikeRadio, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        managePanel.add(yachtRadio, gbc);
        gbc.gridx++;
        managePanel.add(otherRadio, gbc);

        // Remove Field
        gbc.gridx = 0;
        gbc.gridy++;
        managePanel.add(new JLabel("Remove Index:"), gbc);
        gbc.gridx++;
        gbc.weightx = 0;
        removeField.setPreferredSize(new Dimension(200, 30));
        managePanel.add(removeField, gbc);

        // Buttons spanning two columns
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        managePanel.add(addButton, gbc);

        gbc.gridy++;
        managePanel.add(removeButton, gbc);

        gbc.gridy++;
        managePanel.add(saveButton, gbc);

        gbc.gridy++;
        managePanel.add(loadButton, gbc);

        return managePanel;
    }


    private void updateDisplayArea(String tabTitle) {
        switch (tabTitle) {
            case "All":
                displayArea.setText(getSpecifiedVehicleInfo("Vehicle"));
                break;
            case "Cars":
                displayArea.setText(getSpecifiedVehicleInfo("Car"));
                break;
            case "Bikes":
                displayArea.setText(getSpecifiedVehicleInfo("Bike"));
                break;
            case "Yachts":
                displayArea.setText(getSpecifiedVehicleInfo("Yacht"));
                break;
            case "Other":
                displayArea.setText(getSpecifiedVehicleInfo("Other"));
        }
    }

    // REQUIRES: a valid type
    // EFFECTS: returns a string containing the brand, name, year, price of the vehicles in the garage
    // if the type is of 'Vehicle' then it will append the current vehicle's type to the string
    public String getSpecifiedVehicleInfo(String type) {
        StringBuilder info = new StringBuilder();
        int index = 1;
        info.append(type).append("s:").append("\n");

        for (Vehicle vehicle : vehicleStorage.getVehicles()) {
            if (vehicle.getTypeName().equals(type) | type.equals("Vehicle")) {
                info.append(index).append(")").append("   ");
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