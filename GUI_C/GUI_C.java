package GUI_C;
import javax.swing.*;
import client.MessageListener;
import client.Client;


public class GUI_C implements MessageListener{
    
    JFrame frame = new JFrame("Client GUI");
    JPanel panel = new JPanel();

    public GUI_C() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical stacking
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.add(new JScrollPane(panel)); // Add scrolling if needed
        frame.setVisible(true);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Message received in Client GUI: " + message);
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
            GUI_C gui = new GUI_C();
            Client.addMessageListener(gui);
            System.out.println("Client GUI started");
        });
    }

}
