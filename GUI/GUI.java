package GUI;
import java.awt.Frame;
import java.awt.Label;
import java.util.Scanner;
import java.awt.FlowLayout;
import server.MessageListener;
import server.Server;


// Create frame as the top level, components are then subclasses of this

public class GUI extends Frame implements MessageListener{
    
    // private variables...
    private Label label;

    // Constructor to setup GUI components and events handlers
    public GUI() {
        
        // Server server = new Server();

        setTitle("My GUI Window");
        setSize(1000, 1000);      // Set window size
        

        setLayout(new FlowLayout());

        label = new Label("hello_mate", Label.CENTER); // Create the label
        add(label); // Add the label to the Frame

        Label label2 = new Label("hello_world", Label.LEFT);
        add(label2);
        
        setVisible(true);       // Make window visible

    }

    @Override
    public void onMessage(String Message) {
        System.out.println("Message received in GUI: " + Message); // Print to console
        Label label3 = new Label("Message recieved", Label.LEFT);
        add(label3);
    }

    public static void main(String[] args) {

        GUI gui = new GUI();
        System.out.println("GUI started"); 
        Server.addMessageListener(gui);

    }

}
