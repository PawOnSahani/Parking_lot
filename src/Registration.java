import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Registration extends JFrame {

    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/parking";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Pawan@7176450";

    // GUI components
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Registration() {
        // Initialize the frame
        setTitle("Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new FlowLayout());

        // Create registration components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JTextField registrationUsernameField = new JTextField(20);
        JPasswordField registrationPasswordField = new JPasswordField(20);
        JButton registrationButton = new JButton("Register");

        // Add registration components to the frame
        add(usernameLabel);
        add(registrationUsernameField);
        add(passwordLabel);
        add(registrationPasswordField);
        add(registrationButton);

        // Registration button action listener
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = registrationUsernameField.getText();
                String password = new String(registrationPasswordField.getPassword());

                if (registerUser(username, password)) {
                    JOptionPane.showMessageDialog(null, "Registration successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");
                }
            }
        });
    }

    private boolean registerUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "INSERT INTO User (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Registration().setVisible(true);
            }
        });
    }
}
