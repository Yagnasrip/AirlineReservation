import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import com.toedter.calendar.JDateChooser;

public class FlightBooking extends JFrame {

    JComboBox<String> CBFrom, CBTo, CBClass, CBAdult, CBChildren, CBInfant;
    JDateChooser dateChooser;
    JTextArea ticketArea;
    JButton btnPrint, btnSave, btnBack, btnGenerate;

    public FlightBooking() {
        setTitle("Flight Booking");
        setSize(700, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        panel.setBackground(new Color(245, 255, 250));

        String[] cities = {"Bali", "Bangkok", "Cairo", "CapeTown", "Chicago", "Dubai", "Frankfurt", "HongKong", "Istanbul",
                "London", "LosAngeles", "Melbourne", "New York", "Paris", "Rome", "SanFrancisco", "Shanghai", "Singapore", "Sydney", "Toronto"};
        String[] classes = {"Economy", "Business"};
        String[] count = {"0", "1", "2", "3", "4", "5"};

        CBFrom = new JComboBox<>(cities);
        CBTo = new JComboBox<>(cities);
        CBClass = new JComboBox<>(classes);
        CBAdult = new JComboBox<>(count);
        CBChildren = new JComboBox<>(count);
        CBInfant = new JComboBox<>(count);
        dateChooser = new JDateChooser();

        CBAdult.setSelectedIndex(1); // Default 1 adult

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("From:"), gbc);
        gbc.gridx = 1; panel.add(CBFrom, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("To:"), gbc);
        gbc.gridx = 1; panel.add(CBTo, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("Class:"), gbc);
        gbc.gridx = 1; panel.add(CBClass, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("Adults:"), gbc);
        gbc.gridx = 1; panel.add(CBAdult, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("Children:"), gbc);
        gbc.gridx = 1; panel.add(CBChildren, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("Infants:"), gbc);
        gbc.gridx = 1; panel.add(CBInfant, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("Travel Date:"), gbc);
        gbc.gridx = 1; panel.add(dateChooser, gbc); row++;

        ticketArea = new JTextArea(20, 50);
        ticketArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        ticketArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ticketArea);
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        btnGenerate = new JButton("Generate Ticket");
        btnGenerate.addActionListener(e -> generateTicket());

        btnPrint = new JButton("Print Ticket");
        btnPrint.addActionListener(e -> printTicket());

        btnSave = new JButton("Save Ticket as Image");
        btnSave.addActionListener(e -> saveTicketAsImage());

        btnBack = new JButton("Back to Main Menu");
        btnBack.addActionListener(e -> {
            dispose();
            new MainMenu(); // Ensure MainMenu class exists
        });

        add(panel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(245, 255, 250));
        bottomPanel.add(btnGenerate);
        bottomPanel.add(btnPrint);
        bottomPanel.add(btnSave);
        bottomPanel.add(btnBack);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void generateTicket() {
        String from = CBFrom.getSelectedItem().toString();
        String to = CBTo.getSelectedItem().toString();
        String travelClass = CBClass.getSelectedItem().toString();
        int adults = Integer.parseInt(CBAdult.getSelectedItem().toString());
        int children = Integer.parseInt(CBChildren.getSelectedItem().toString());
        int infants = Integer.parseInt(CBInfant.getSelectedItem().toString());
        Date travelDate = dateChooser.getDate();

        if (from.equals(to)) {
            JOptionPane.showMessageDialog(this, "From and To locations must differ.");
            return;
        }

        if (travelDate == null) {
            JOptionPane.showMessageDialog(this, "Please select a travel date.");
            return;
        }

        if (adults + children + infants == 0) {
            JOptionPane.showMessageDialog(this, "Please select at least one passenger.");
            return;
        }

        String airline = "Vistara";
        String departure = "12:00";
        String arrival = "14:00";
        String bookingId = "BK" + System.currentTimeMillis();
        Date now = new Date();

        int fare = adults * 5000 + children * 3000 + infants * 1000;

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat timeStamp = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

        String ticket = String.format(
            "✅ Available Flight:\n" +
            "Airline     : %s\n" +
            "Departure   : %s\n" +
            "Arrival     : %s\n\n" +
            "-------------------------------------\n" +
            "          ✈️ AIRLINE TICKET\n" +
            "-------------------------------------\n" +
            "From       : %s\n" +
            "To         : %s\n" +
            "Date       : %s\n" +
            "Class      : %s\n" +
            "Adults     : %d\n" +
            "Children   : %d\n" +
            "Infants    : %d\n" +
            "-------------------------------------\n" +
            "Fare       : ₹%d\n" +
            "Booking ID : %s\n" +
            "Date Booked: %s\n" +
            "-------------------------------------\n",
            airline, departure, arrival,
            from, to, sdf.format(travelDate), travelClass,
            adults, children, infants,
            fare, bookingId, timeStamp.format(now)
        );

        ticketArea.setText(ticket);
    }

    private void printTicket() {
        try {
            boolean printed = ticketArea.print();
            if (printed) {
                JOptionPane.showMessageDialog(this, "Ticket printed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Print canceled.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Print failed.");
        }
    }

    private void saveTicketAsImage() {
        BufferedImage image = new BufferedImage(ticketArea.getWidth(), ticketArea.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        ticketArea.paint(g2);
        g2.dispose();

        try {
            String filename = "Ticket_" + System.currentTimeMillis() + ".png";
            ImageIO.write(image, "png", new File(filename));
            JOptionPane.showMessageDialog(this, "Ticket saved as image:\n" + filename);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving ticket image.");
        }
    }

    public static void main(String[] args) {
        new FlightBooking();
    }
}
