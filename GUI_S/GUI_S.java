package GUI_S;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;
import server.MessageListener;

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
        // Ensure the GUI work is on the EDT thread, and all the other stuff is on a seperate thread 
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("Updating GUI...");
                JLabel label = new JLabel(message, JLabel.LEFT);
                panel.add(label);
                panel.revalidate(); // Refresh layout
                panel.repaint();    // Redraw

            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        });
    }

public static void main(String[] args) throws IOException {
    GUI_S gui = new GUI_S();

    // Start the socket reading in a background thread
    new Thread(() -> {
        try {
            Socket socket = new Socket("localhost", 6666);
            DataInputStream in = new DataInputStream(socket.getInputStream());

            System.out.println("Server GUI started");

            if (socket.isConnected()) {
                System.out.println("[SERVER GUI] Connected with Server");
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