import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketDesignPanel extends JPanel {
    private String from, to, travelClass, seat, gate, bookingId;
    private int adults, children, infants;
    private Date travelDate;

    public TicketDesignPanel(String from, String to, String travelClass,
                             int adults, int children, int infants,
                             Date travelDate, String bookingId, String seat, String gate) {
        this.from = from;
        this.to = to;
        this.travelClass = travelClass;
        this.adults = adults;
        this.children = children;
        this.infants = infants;
        this.travelDate = travelDate;
        this.bookingId = bookingId;
        this.seat = seat;
        this.gate = gate;

        setPreferredSize(new Dimension(600, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Background
        g2.setColor(new Color(245, 255, 250));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Title
        g2.setColor(new Color(0, 51, 102));
        g2.setFont(new Font("SansSerif", Font.BOLD, 22));
        g2.drawString("🛫 AIRLINE BOARDING PASS", 150, 40);

        // Ticket Details
        g2.setFont(new Font("Monospaced", Font.PLAIN, 16));
        g2.setColor(Color.DARK_GRAY);
        int y = 90;
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

        g2.drawString("From     : " + from, 50, y); y += 30;
        g2.drawString("To       : " + to, 50, y); y += 30;
        g2.drawString("Date     : " + (travelDate != null ? sdf.format(travelDate) : "N/A"), 50, y); y += 30;
        g2.drawString("Class    : " + travelClass, 50, y); y += 30;
        g2.drawString("Passengers: Adults: " + adults + ", Children: " + children + ", Infants: " + infants, 50, y); y += 30;
        g2.drawString("Seat     : " + seat + "   Gate: " + gate, 50, y); y += 30;
        g2.drawString("Booking ID: " + bookingId, 50, y); y += 40;

        // Footer
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2.setColor(new Color(0, 128, 0));
        g2.drawString("Thank you for flying with us!", 180, y);
    }
}
