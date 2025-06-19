package Exhibition.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.swing.filechooser.FileNameExtensionFilter;

import Exhibition.model.Participant;
import Exhibition.db.DatabaseManager;

public class ExhibitionGUI extends JFrame {
    private DatabaseManager dbManager;
    private JTextField regIdField, nameField, facultyField, projectField, contactField, emailField;
    private JLabel imageLabel;
    private JButton browseButton, registerButton, searchButton, updateButton, deleteButton, clearButton, exitButton;
    private String imagePath;

    private static final String IMAGE_FOLDER = "images";

    public ExhibitionGUI() {
        dbManager = new DatabaseManager();
        initializeComponents();
        setupUI();

        new File(IMAGE_FOLDER).mkdirs();
    }

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
        setTitle("Victoria University Exhibition Registration System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.add(browseButton, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(registerButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        browseButton.addActionListener(e -> browseImage());
        registerButton.addActionListener(e -> registerParticipant());
        searchButton.addActionListener(e -> searchParticipant());
        updateButton.addActionListener(e -> updateParticipant());
        deleteButton.addActionListener(e -> deleteParticipant());
        clearButton.addActionListener(e -> clearForm());
        exitButton.addActionListener(e -> System.exit(0));

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void browseImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();

            try {
                // Copying image to local folder
                Path destination = new File(IMAGE_FOLDER + File.separator + fileName).toPath();
                Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
                imagePath = destination.toString();

                ImageIcon icon = new ImageIcon(imagePath);
                Image image = icon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(image));
                imageLabel.setText("");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error copying image: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void registerParticipant() {
        String regId = regIdField.getText().trim();
        String name = nameField.getText().trim();
        String faculty = facultyField.getText().trim();
        String project = projectField.getText().trim();
        String contact = contactField.getText().trim();
        String email = emailField.getText().trim();

        if (regId.isEmpty() || name.isEmpty() || faculty.isEmpty() || project.isEmpty() ||
            contact.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Participant p = new Participant(regId, name, faculty, project, contact, email, imagePath);
        if (dbManager.addParticipant(p)) {
            JOptionPane.showMessageDialog(this, "Participant registered successfully.");
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchParticipant() {
        String regId = regIdField.getText().trim();
        if (regId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Registration ID to search.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Participant p = dbManager.searchParticipant(regId);
        if (p != null) {
            nameField.setText(p.getName());
            facultyField.setText(p.getFaculty());
            projectField.setText(p.getProjectTitle());
            contactField.setText(p.getContact());
            emailField.setText(p.getEmail());
            imagePath = p.getImagePath();

            if (imagePath != null && !imagePath.isEmpty()) {
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    ImageIcon icon = new ImageIcon(imagePath);
                    Image img = icon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(img));
                    imageLabel.setText("");
                } else {
                    imageLabel.setIcon(null);
                    imageLabel.setText("Image not found");
                }
            } else {
                imageLabel.setIcon(null);
                imageLabel.setText("No image selected");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No participant found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateParticipant() {
        String regId = regIdField.getText().trim();
        String name = nameField.getText().trim();
        String faculty = facultyField.getText().trim();
        String project = projectField.getText().trim();
        String contact = contactField.getText().trim();
        String email = emailField.getText().trim();

        if (regId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Registration ID to update.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Participant p = new Participant(regId, name, faculty, project, contact, email, imagePath);
        if (dbManager.updateParticipant(p)) {
            JOptionPane.showMessageDialog(this, "Participant updated successfully.");
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteParticipant() {
        String regId = regIdField.getText().trim();
        if (regId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Registration ID to delete.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this participant?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dbManager.deleteParticipant(regId)) {
                JOptionPane.showMessageDialog(this, "Participant deleted.");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExhibitionGUI().setVisible(true));
    }
}
