package server;
import java.io.*;
import java.net.*;
import java.util.*;


// Server class creates server socket, and accepts client connections, making a new thread for each client
public class Server {

    // Add client output streams into a list for multithreading 
    static List<DataOutputStream> clientOutputs = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("Server started");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("[SERVER] Client connected");

            // Start a new thread for each client
            new Thread(new ClientHandler(socket)).start();
        
        }
    }
}

// Handles communication with a single client
// Extend the threat using runnable
class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Add client output stream to list
            Server.clientOutputs.add(out);

            String username_msg = "Username: " + in.readUTF();
            System.out.println("[SERVER] " + username_msg);

            while (socket.isConnected()) {
                String msg = in.readUTF();
                System.out.println("[SERVER] " + msg);
                broadcast(msg, out);

                if (msg.toLowerCase().contains("exit")) {
                    for (DataOutputStream dos : Server.clientOutputs) {
                        System.out.println("[SERVER] OutputStream: " + dos);
                    }
                    System.out.println("[SERVER] Client disconnected");
                    break;
                }
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("[SERVER] Connection error: " + e.getMessage());
        }
    }

    // Broadcast message to all clients
    private void broadcast(String message, DataOutputStream senderout) {
        synchronized (Server.clientOutputs) {
            for (DataOutputStream clientOut : Server.clientOutputs) {
                if (clientOut != senderout)
                try {
                    
                    clientOut.writeUTF(message);
                    clientOut.flush();
                } catch (IOException e) {
                    // Ignore failed sends
                }
            }
        }
    }
}