package Exhibition.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import Exhibition.model.Participant;
import Exhibition.db.DatabaseManager;
import Exhibition.util.InputValidator;

public class ExhibitionGUI extends JFrame {
    // Private fields for all components
    private DatabaseManager dbManager;
    private JTextField regIdField;
    private JTextField nameField;
    private JTextField facultyField;
    private JTextField projectField;
    private JTextField contactField;
    private JTextField emailField;
    private JLabel imageLabel;
    private JButton browseButton;
    private JButton registerButton;
    private JButton searchButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;
    private JButton exitButton;
    private String imagePath;

    public ExhibitionGUI() {
        dbManager = new DatabaseManager();
        initializeComponents();
        setupUI();
    }

    // Getters for all components
    public JTextField getRegIdField() {
        return regIdField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getFacultyField() {
        return facultyField;
    }

    public JTextField getProjectField() {
        return projectField;
    }

    public JTextField getContactField() {
        return contactField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JLabel getImageLabel() {
        return imageLabel;
    }

    public JButton getBrowseButton() {
        return browseButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public String getImagePath() {
        return imagePath;
    }

    // Setters for mutable fields
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Initialize all components
    private void initializeComponents() {
        regIdField = new JTextField();
        nameField = new JTextField();
        facultyField = new JTextField();
        projectField = new JTextField();
        contactField = new JTextField();
        emailField = new JTextField();
        
        imageLabel = new JLabel("No image selected", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(300, 200));
        imageLabel.setBorder(BorderFactory.createEtchedBorder());
        
        browseButton = new JButton("Browse Image");
        registerButton = new JButton("Register");
        searchButton = new JButton("Search");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");
    }

    private void setupUI() {
        setTitle("Victoria University Exhibition Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form panel with GridLayout
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        formPanel.add(new JLabel("Registration ID:"));
        formPanel.add(regIdField);
        formPanel.add(new JLabel("Student Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Faculty:"));
        formPanel.add(facultyField);
        formPanel.add(new JLabel("Project Title:"));
        formPanel.add(projectField);
        formPanel.add(new JLabel("Contact Number:"));
        formPanel.add(contactField);
        formPanel.add(new JLabel("Email Address:"));
        formPanel.add(emailField);

        // Image panel
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.add(browseButton, BorderLayout.SOUTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(registerButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        // Add action listeners
        browseButton.addActionListener(e -> browseImage());
        registerButton.addActionListener(e -> registerParticipant());
        searchButton.addActionListener(e -> searchParticipant());
        updateButton.addActionListener(e -> updateParticipant());
        deleteButton.addActionListener(e -> deleteParticipant());
        clearButton.addActionListener(e -> clearForm());
        exitButton.addActionListener(e -> System.exit(0));

        // Add components to main panel
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Action methods (same as before)
    private void browseImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            ImageIcon icon = new ImageIcon(imagePath);
            Image image = icon.getImage().getScaledInstance(
                imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));
            imageLabel.setText("");
        }
    }

    private void registerParticipant() {
        // Implementation remains the same as before
    }

    private void searchParticipant() {
        // Implementation remains the same as before
    }

    private void updateParticipant() {
        // Implementation remains the same as before
    }

    private void deleteParticipant() {
        // Implementation remains the same as before
    }

    private void clearForm() {
        regIdField.setText("");
        nameField.setText("");
        facultyField.setText("");
        projectField.setText("");
        contactField.setText("");
        emailField.setText("");
        imageLabel.setIcon(null);
        imageLabel.setText("No image selected");
        imagePath = "";
    }
}
