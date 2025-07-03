package GUI;
import java.awt.Frame;
import java.awt.Label;
import java.awt.FlowLayout;

// Create frame as the top level, components are then subclasses of this

public class GUI extends Frame{
    
    // private variables...
    private Label label;

    // Constructor to setup GUI components and events handlers
    public GUI() {
        setTitle("My GUI Window");
        setSize(1000, 1000);      // Set window size

        setLayout(new FlowLayout());

        label = new Label("hello", Label.CENTER); // Create the label
        add(label); // Add the label to the Frame

        Label label2 = new Label("hello_world", Label.LEFT);
        add(label2);
         
        setVisible(true);       // Make window visible

    }

    public static void main(String[] args) {

        new GUI();
    }
}
