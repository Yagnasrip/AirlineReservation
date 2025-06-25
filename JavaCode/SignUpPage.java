import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SignUpPage extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton signUpBtn;
    private static final String USERS_FILE = "users.txt";

    public SignUpPage() {
        setTitle("Sign Up - Airline Reservation");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel userLabel = new JLabel("New Username:");
        userField = new JTextField(20);

        JLabel passLabel = new JLabel("New Password:");
        passField = new JPasswordField(20);

        signUpBtn = new JButton("Create Account");
        signUpBtn.setBackground(new Color(34, 139, 34));
        signUpBtn.setForeground(Color.WHITE);
        signUpBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signUpBtn.addActionListener(e -> registerUser());

        gbc.gridx = 0; gbc.gridy = 0; panel.add(userLabel, gbc);
        gbc.gridx = 1; panel.add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(passLabel, gbc);
        gbc.gridx = 1; panel.add(passField, gbc);

        gbc.gridx = 1; gbc.gridy = 2; panel.add(signUpBtn, gbc);

        add(panel);
        setVisible(true);
    }

    private void registerUser() {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isUserExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(username + "," + password);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Account created successfully!");
            dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving user.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean isUserExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.split(",")[0].trim().equalsIgnoreCase(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File may not exist yet; that's okay.
        }
        return false;
    }

    public static void main(String[] args) {
        new SignUpPage();
    }
}
