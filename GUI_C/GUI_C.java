package GUI_C;
import java.io.DataInputStream;
import java.net.Socket;
import java.io.IOException;
import javax.swing.*;

public class GUI_C{
    
    JFrame frame = new JFrame("Client GUI");
    JPanel panel = new JPanel();
    private Socket socket;

    public GUI_C(Socket socket) {
        this.socket = socket;
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical stacking
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.add(new JScrollPane(panel)); // Add scrolling if needed
        frame.setVisible(true);
    }

    public void onMessage(String message) {
        System.out.println("Message received in Client GUI: " + message);
        SwingUtilities.invokeLater(() -> {
            try {
                JLabel label = new JLabel(message, JLabel.LEFT);
                panel.add(label);
                panel.revalidate();
                panel.repaint();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        });
    }

public static void startWithSocket(Socket socket){
    GUI_C gui = new GUI_C(socket);

    // Start the socket reading in a background thread
    new Thread(() -> {
        try {
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