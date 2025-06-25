import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class InternationalFlight extends JFrame {

    private JTable table;
    private JLabel totalFlightsLabel;
    private JLabel earliestTimeLabel;
    private JLabel latestTimeLabel;
    private JLabel avgPriceLabel;

    public InternationalFlight() {
        setTitle("International Flights");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Table data
        String[] columns = {"Flight No", "Airline", "From", "To", "Departure Time", "Price"};
        Object[][] data = {
            {"I201", "Emirates", "Delhi", "Dubai", "06:30", 18500},
            {"I202", "Singapore Airlines", "Chennai", "Singapore", "09:45", 22000},
            {"I203", "Lufthansa", "Mumbai", "Frankfurt", "01:15", 44500},
            {"I204", "Qatar Airways", "Bangalore", "Doha", "04:20", 30500},
            {"I205", "Air India", "Delhi", "New York", "10:00", 63000},
            {"I206", "British Airways", "Mumbai", "London", "14:35", 54000},
            {"I207", "Air France", "Bangalore", "Paris", "13:10", 52000},
            {"I208", "Cathay Pacific", "Kolkata", "Hong Kong", "12:00", 29500},
            {"I209", "Japan Airlines", "Delhi", "Tokyo", "07:30", 56000},
            {"I210", "Qantas", "Hyderabad", "Sydney", "22:45", 67000},
            {"I211", "Etihad", "Chennai", "Abu Dhabi", "08:25", 21500},
            {"I212", "United Airlines", "Mumbai", "Chicago", "03:40", 71000},
            {"I213", "Thai Airways", "Kolkata", "Bangkok", "20:10", 18500},
            {"I214", "Turkish Airlines", "Delhi", "Istanbul", "06:00", 48000},
            {"I215", "KLM", "Bangalore", "Amsterdam", "11:20", 55000}
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
        new InternationalFlight();
    }
}
