import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class DomesticFlight extends JFrame {

    private JTable table;
    private JLabel totalFlightsLabel;
    private JLabel earliestTimeLabel;
    private JLabel latestTimeLabel;
    private JLabel avgPriceLabel;

    public DomesticFlight() {
        setTitle("Domestic Flights");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Flight Data
        String[] columns = {"Flight No", "Airline", "From", "To", "Departure Time", "Price"};
        Object[][] data = {
            {"D101", "IndiGo", "Chennai", "Delhi", "06:00", 4500},
            {"D102", "Air India", "Mumbai", "Bangalore", "09:15", 5200},
            {"D103", "SpiceJet", "Kolkata", "Hyderabad", "13:45", 4800},
            {"D104", "Vistara", "Delhi", "Chennai", "17:00", 5300},
            {"D105", "GoAir", "Bangalore", "Mumbai", "20:30", 4900},
            {"D106", "AirAsia", "Hyderabad", "Kolkata", "07:00", 4300},
            {"D107", "IndiGo", "Ahmedabad", "Goa", "12:15", 5100},
            {"D108", "SpiceJet", "Lucknow", "Delhi", "15:50", 4400},
            {"D109", "Vistara", "Pune", "Bangalore", "18:10", 4600},
            {"D110", "Air India", "Jaipur", "Mumbai", "21:30", 4700}
        };

        // Table setup
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.setGridColor(Color.LIGHT_GRAY);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(220, 240, 255));

        JScrollPane scrollPane = new JScrollPane(table);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Flight Summary"));
        summaryPanel.setBackground(new Color(240, 248, 255));

        totalFlightsLabel = new JLabel();
        earliestTimeLabel = new JLabel();
        latestTimeLabel = new JLabel();
        avgPriceLabel = new JLabel();

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        for (JLabel label : new JLabel[]{totalFlightsLabel, earliestTimeLabel, latestTimeLabel, avgPriceLabel}) {
            label.setFont(labelFont);
            summaryPanel.add(label);
        }

        calculateSummary(data);

        // Back Button
        JPanel backPanel = new JPanel();
        JButton backButton = new JButton("← Back to Main Menu");
        backButton.setBackground(new Color(0, 102, 204));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        backButton.addActionListener(e -> {
            dispose();
            new MainMenu();
        });
        backPanel.add(backButton);

        // Layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(summaryPanel, BorderLayout.NORTH);
        mainPanel.add(backPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void calculateSummary(Object[][] data) {
        int totalFlights = data.length;
        String earliestTime = (String) data[0][4];
        String latestTime = (String) data[0][4];
        double totalPrice = 0;

        for (Object[] row : data) {
            String time = (String) row[4];
            if (time.compareTo(earliestTime) < 0) earliestTime = time;
            if (time.compareTo(latestTime) > 0) latestTime = time;
            totalPrice += Double.parseDouble(row[5].toString());
        }

        double avgPrice = totalPrice / totalFlights;
        DecimalFormat df = new DecimalFormat("#.00");

        totalFlightsLabel.setText("🛫 Total Flights: " + totalFlights);
        earliestTimeLabel.setText("⏰ Earliest Departure: " + earliestTime);
        latestTimeLabel.setText("🕘 Latest Departure: " + latestTime);
        avgPriceLabel.setText("💵 Average Price: ₹" + df.format(avgPrice));
    }

    public static void main(String[] args) {
        new DomesticFlight();
    }
}
