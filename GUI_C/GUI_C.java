package GUI_C;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.IOException;
import javax.swing.*;
import java.awt.event.*;

public class GUI_C extends JPanel implements ActionListener{
    
    JFrame frame = new JFrame("Client GUI");
    JPanel panel = new JPanel(); // Parent Panel that holds everything, parents panel organises everything
    private JPanel messagePanel; // Dedicated panel for input messages
    private JTextField textField;
    private Socket socket;
    private String userName;
    private DataInputStream in;
    private DataOutputStream out;

    // private boolean userbool = false;
    // private String userName = "username";

    public GUI_C(Socket socket, String userName) throws IOException {
        this.socket = socket;
        this.userName = userName;

        try {

            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {

            System.err.println("Error creating streams: " + e.getMessage());
            e.printStackTrace();

        }

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical stacking

        // Seperate panels created for organisation
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        
        // Scrollable pane
        panel.add(new JScrollPane(messagePanel)); // Add scrolling if needed

        // Create and add text field at the bottom
        textField = new JTextField(20);
        textField.addActionListener(this);

        textField.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));
        panel.add(textField);

        JLabel label = new JLabel("[SERVER] You joined the chatroom!", JLabel.LEFT);
        messagePanel.add(label);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.add(panel);
        frame.setVisible(true);

        // Store reference to message panel for adding messages
        this.messagePanel = messagePanel;
    }

    public void actionPerformed(ActionEvent evt) {
    String text = textField.getText();
    System.out.println("[CLIENT GUI]: (input) " + text);
    textField.setText("");

        // Send message to server/client (only run this for actual messages)
        try {
            if (socket != null && !socket.isClosed()) {
                // userName
                String message = (userName + ": " + text);
                onMessage(message);

                out.writeUTF(message);
                out.flush();
            }
        } catch (IOException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }

    public void onMessage(String message) {
        System.out.println("[CLIENT GUI]: " + message);
        SwingUtilities.invokeLater(() -> {
            try {
                JLabel label = new JLabel(message, JLabel.LEFT);
                messagePanel.add(label);
                messagePanel.revalidate();
                messagePanel.repaint();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        });
    }

public static void startWithSocket(Socket socket, String userName) {
    try {
        // DataInputStream in = new DataInputStream(socket.getInputStream());
        // DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        GUI_C gui = new GUI_C(socket, userName);

        // Start the socket reading in a background thread
        new Thread(() -> {
            try {
                System.out.println("Client GUI started");

                if (socket.isConnected()) {
                    System.out.println("[CLIENT GUI] Connected with Server");
                }

                // Keep reading messages from the server and update the GUI
                while (true) {
                    String message = gui.in.readUTF();
                    gui.onMessage(message);
                }

                // socket.close(); // Only close when you want to exit the GUI

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    } catch (IOException e) {
        e.printStackTrace();
    }
}}