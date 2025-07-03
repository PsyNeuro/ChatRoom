package server;
import java.io.*;
import java.net.*;
import java.util.*;


// Server class creates server socket, and accpets client connections, making a new thread for each client
public class Server {

    static List<DataOutputStream> clientOutputs = Collections.synchronizedList(new ArrayList<>());


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("Server started");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");
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

            Server.clientOutputs.add(out);
            System.out.println("Handling client in thread: " + Thread.currentThread().getName());

            System.out.println("Username: " + in.readUTF());

            while (socket.isConnected()) {
                String msg = in.readUTF();
                System.out.println(msg);
                broadcast(msg);

                if (msg.toLowerCase().contains("exit")) {
                    for (DataOutputStream dos : Server.clientOutputs) {
                        System.out.println("OutputStream: " + dos);
                    }
                    System.out.println("Client disconnected");
                    break;
                }
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    // Broadcast message to all clients
    private void broadcast(String message) {
        synchronized (Server.clientOutputs) {
            for (DataOutputStream clientOut : Server.clientOutputs) {
                try {
                    clientOut.writeUTF(message);
                } catch (IOException e) {
                    // Ignore failed sends
                }
            }
        }
    }

}