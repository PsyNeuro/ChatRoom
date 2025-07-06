package GUI;
import server.MessageListener;
import server.Server;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

// Create frame as the top level, components are then subclasses of this

public class GUI implements MessageListener{
    
    JFrame frame = new JFrame("GUI_frame");
    JPanel panel = new JPanel();
    
    // Constructor to setup GUI components and events handlers
    public GUI() {
        
        // Server server = new Server();
        // setTitle("My GUI Window");
        // setSize(1000, 1000);      // Set window size
        //Display the window.
        // setLayout(new GridLayout(0, 1)); // Stack labels vertically, one column and any number of rows
        // setVisible(true);       // Make window visible

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical stacking
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.add(new JScrollPane(panel)); // Add scrolling if needed
        frame.setVisible(true);

    }

    @Override
    public void onMessage(String Message) {

        System.out.println("Message received in GUI: " + Message); // Print to console

        try {
            JLabel label = new JLabel(Message, JLabel.LEFT);
            panel.add(label);
            panel.revalidate(); // Refresh layout
            panel.repaint();    // Redraw
    
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

        public void run() {

        GUI gui = new GUI();
        System.out.println("GUI started"); 
        Server.addMessageListener(gui);

        };
        
        });


    }

}
