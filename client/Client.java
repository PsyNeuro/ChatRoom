package client;
import java.io.*;
import java.net.*;
import java.util.*;

import GUI_C.GUI_C;

public class Client {

    public static void main(String[] args) throws IOException {
       // Create a socket to connect to the server, its on port 6666
       Socket socket = new Socket("localhost", 6666);
       System.out.println("[CLIENT] Connected to server");
       
       // Create output and input streams for communication
       DataOutputStream out = new DataOutputStream(socket.getOutputStream());
       DataInputStream in = new DataInputStream(socket.getInputStream());

       // GUI_C.startWithSocket(socket);
       GUI_C gui = new GUI_C(socket);

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
       Client_listen client_listen = new Client_listen(in, gui);
       Thread thread = new Thread(client_listen);
       thread.start();

       while (socket.isConnected()) {

            String msg = myObj.nextLine();
            out.writeUTF(userName + ": " + msg);
            out.flush();
            

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