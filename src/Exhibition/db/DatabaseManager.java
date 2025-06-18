package Exhibition.db;

import java.sql.*;
import Exhibition.model.Participant;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        initializeDatabase();
    }

    // Initialize DB connection
    private void initializeDatabase() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            //  Use absolute path to avoid writing to a copied version
            String dbPath = "C:\\Users\\TADDY\\eclipse-workspace\\Exhibition/VUE_Exhibition.accdb"; // <- replace this with your actual path
            connection = DriverManager.getConnection("jdbc:ucanaccess://" + dbPath);

            System.out.println("Database connected successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Add a participant to the database
    public boolean addParticipant(Participant participant) {
        String sql = "INSERT INTO Participants (RegistrationID, StudentName, Faculty, ProjectTitle, ContactNumber, Email, ImagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, participant.getRegistrationId());
            stmt.setString(2, participant.getName());
            stmt.setString(3, participant.getFaculty());
            stmt.setString(4, participant.getProjectTitle());
            stmt.setString(5, participant.getContact());
            stmt.setString(6, participant.getEmail());
            stmt.setString(7, participant.getImagePath());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Participant added successfully: " + participant);
                return true;
            } else {
                System.out.println("Failed to add participant.");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("SQL Error during insert:");
            ex.printStackTrace();
            return false;
        }
    }

    // Search a participant by Registration ID
    public Participant searchParticipant(String registrationId) {
        String sql = "SELECT * FROM Participants WHERE RegistrationID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, registrationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Participant(
                    rs.getString("RegistrationID"),
                    rs.getString("StudentName"),
                    rs.getString("Faculty"),
                    rs.getString("ProjectTitle"),
                    rs.getString("ContactNumber"),
                    rs.getString("Email"),
                    rs.getString("ImagePath")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Update participant (placeholder)
    public boolean updateParticipant(Participant participant) {
        // Implement update logic here
        return false;
    }

    // Delete participant (placeholder)
    public boolean deleteParticipant(String registrationId) {
        // Implement delete logic here
        return false;
    }

    // Close database connection when done
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
