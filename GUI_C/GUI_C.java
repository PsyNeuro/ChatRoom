package GUI_C;
import java.io.DataInputStream;
import java.net.Socket;
import java.io.IOException;
import javax.swing.*;
import client.MessageListener;
import client.Client;

public class GUI_C{
    
    JFrame frame = new JFrame("Client GUI");
    JPanel panel = new JPanel();

    public GUI_C() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical stacking
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.add(new JScrollPane(panel)); // Add scrolling if needed
        frame.setVisible(true);
    }

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

public static void main(String[] args) throws IOException {
    GUI_C gui = new GUI_C();

    // Start the socket reading in a background thread
    new Thread(() -> {
        try {
            Socket socket = new Socket("localhost", 6666);
            DataInputStream in = new DataInputStream(socket.getInputStream());

            System.out.println("Client GUI started");

            if (socket.isConnected()) {
                System.out.println("[CLIENT GUI] Connected with Server");
            }

            // Keep reading messages from the server and update the GUI
            while (true) {
                String message = in.readUTF();
                gui.onMessage(message);
            }

            // socket.close(); // Only close when you want to exit the GUI

        } catch (IOException e) {
            e.printStackTrace();
        }
    }).start();
}}