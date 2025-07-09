package client;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    // Create a list to have the listeners 
    static List<MessageListener> listeners = new ArrayList<>();

    // A function that adds people to the listeners list
    public static void addMessageListener(MessageListener listener) {
            listeners.add(listener);
    }

    // A function that sends a message to those listeners via messagelistener and onMessage
    public static void notifyListeners(String message) {

        for (MessageListener listener : listeners) {
            listener.onMessage(message);
        }
    }

    public static void main(String[] args) throws IOException {
       // Create a socket to connect to the server, its on port 6666
       Socket socket = new Socket("localhost", 6666);
       System.out.println("[CLIENT] Connected to server");
       Client.notifyListeners("Connected to server");
       

       // Create output and input streams for communication
       DataOutputStream out = new DataOutputStream(socket.getOutputStream());
       DataInputStream in = new DataInputStream(socket.getInputStream());

       // Create Scanner once
       Scanner myObj = new Scanner(System.in);  

       // Get username
       System.out.println("[CLIENT] Enter username");
       String userName = myObj.nextLine();
       
       out.writeUTF(userName);
       out.flush();
       out.writeUTF(userName + " has joined the chat!");
       out.flush();


       // Create listening thread
       Client_listen client_listen = new Client_listen(in);
       Thread thread = new Thread(client_listen);
       thread.start();

       while (socket.isConnected()) {

            String msg = myObj.nextLine();
            out.writeUTF(userName + ": " + msg);
            out.flush();
            Client.notifyListeners(userName + ": " + msg);


           // Optional: break the loop if user types "exit"
            if (msg.equalsIgnoreCase("exit")) {
                out.writeUTF(userName + ": " + "has left the chat...");
                out.flush();
                break;
            }
       }


       myObj.close();
       socket.close();
   }
}