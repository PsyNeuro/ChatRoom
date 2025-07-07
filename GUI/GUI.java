package GUI;
import javax.swing.*;
import server.MessageListener;
import server.Server;

public class GUI implements MessageListener {
    JFrame frame = new JFrame("GUI_frame");
    JPanel panel = new JPanel();

    public GUI() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical stacking
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.add(new JScrollPane(panel)); // Add scrolling if needed
        frame.setVisible(true);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Message received in GUI: " + message);
        try {
            JLabel label = new JLabel(message, JLabel.LEFT);
            panel.add(label);
            panel.revalidate(); // Refresh layout
            panel.repaint();    // Redraw
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            System.out.println("GUI started");
            Server.addMessageListener(gui);
        });
    }
}