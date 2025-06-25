import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class LoginPage extends JFrame {

    private JTextField userText;
    private JPasswordField passText;
    private JCheckBox rememberMe;
    private JCheckBox showPassword;
    private static final String USERS_FILE = "users.txt";
    private static final String REMEMBER_FILE = "remember.txt";

    public LoginPage() {
        setTitle("Airline Reservation - Login");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel userLabel = new JLabel("User Name:");
        userText = new JTextField(20);

        JLabel passLabel = new JLabel("Password:");
        passText = new JPasswordField(20);

        showPassword = new JCheckBox("Show Password");
        showPassword.setBackground(Color.WHITE);
        showPassword.addActionListener(e -> passText.setEchoChar(showPassword.isSelected() ? (char) 0 : '\u2022'));

        rememberMe = new JCheckBox("Remember Me");
        rememberMe.setBackground(Color.WHITE);

        JButton loginButton = new JButton("Sign In");
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        loginButton.addActionListener(e -> handleLogin());

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(new Color(34, 139, 34));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        signUpButton.addActionListener(e -> new SignUpPage());

        // Layout
        gbc.gridx = 0; gbc.gridy = 0; panel.add(userLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(userText, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(passText, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(showPassword, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(rememberMe, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(loginButton, gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        panel.add(signUpButton, gbc);

        add(panel);
        loadRememberedUser();
        setVisible(true);
    }

    private void handleLogin() {
        String username = userText.getText().trim();
        String password = new String(passText.getPassword()).trim();

        boolean isValid = checkCredentials(username, password);

        if (isValid) {
            if (rememberMe.isSelected()) {
                try (FileWriter writer = new FileWriter(REMEMBER_FILE)) {
                    writer.write(username);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                new File(REMEMBER_FILE).delete();
            }

            JOptionPane.showMessageDialog(this, "Login successful!");
            new MainMenu();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean checkCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].trim().equals(username) && parts[1].trim().equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void loadRememberedUser() {
        File rememberFile = new File(REMEMBER_FILE);
        if (rememberFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(rememberFile))) {
                userText.setText(reader.readLine().trim());
                rememberMe.setSelected(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
