import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Airline Reservation System");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Nimbus Look & Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load Background
        BackgroundPanel bgPanel = new BackgroundPanel("https://www.pixelstalk.net/wp-content/uploads/images6/Travel-HD-Wallpaper-Free-download.jpg");
        bgPanel.setLayout(new GridBagLayout());

        // Layout Settings
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel titleLabel = new JLabel("✈ Welcome to Airline Reservation System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        bgPanel.add(titleLabel, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 20, 20));
        buttonPanel.setOpaque(false);

        buttonPanel.add(createStyledButton("✈ Domestic Flights", new Color(0, 123, 255), () -> {
            dispose();
            new DomesticFlight();
        }));

        buttonPanel.add(createStyledButton("🌐 International Flights", new Color(40, 167, 69), () -> {
            dispose();
            new InternationalFlight();
        }));

        buttonPanel.add(createStyledButton("🧾 Book a Flight", new Color(255, 193, 7), () -> {
            dispose();
            new FlightBooking();
        }));

        buttonPanel.add(createStyledButton("🚪 Exit", new Color(220, 53, 69), () -> {
            System.exit(0);
        }));

        gbc.gridy = 1;
        bgPanel.add(buttonPanel, gbc);

        setContentPane(bgPanel);
        setVisible(true);
    }

    // Utility Method: Styled Buttons with Hover Effects
    private JButton createStyledButton(String text, Color bgColor, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setUI(new BasicButtonUI());
        button.setPreferredSize(new Dimension(300, 50));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Rounded + Hover
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        button.addActionListener(e -> action.run());

        return button;
    }

    // Background Panel
    class BackgroundPanel extends JPanel {
        private Image background;

        public BackgroundPanel(String imageUrl) {
            try {
                background = ImageIO.read(new URL(imageUrl));
            } catch (IOException e) {
                System.out.println("Failed to load background image.");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
