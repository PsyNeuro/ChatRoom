package GUI_S;
import javax.swing.*;
import server.MessageListener;
import server.Server;

public class GUI_S implements MessageListener {
    JFrame frame = new JFrame("Server GUI");
    JPanel panel = new JPanel();

    public GUI_S() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical stacking
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.add(new JScrollPane(panel)); // Add scrolling if needed
        frame.setVisible(true);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Message received in Server GUI: " + message);
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
            GUI_S gui = new GUI_S();
            Server.addMessageListener(gui);
            System.out.println("Server GUI started");
        });
    }
}