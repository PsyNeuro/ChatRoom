package client;
import java.io.*;

public class Client_listen implements Runnable {
    private DataInputStream in;

    public Client_listen(DataInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String smsg = in.readUTF();
                System.out.println(smsg);
            }
        } catch (IOException e) {
            System.out.println("Connection closed or error: " + e.getMessage());
        }
    }
}