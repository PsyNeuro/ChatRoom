package client;
import java.io.*;

import GUI_C.GUI_C;

public class Client_listen implements Runnable {
    private DataInputStream in;
    private GUI_C gui;

    public Client_listen(DataInputStream in, GUI_C gui) {
        this.in = in;
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String smsg = in.readUTF();
                System.out.println("[CLIENT]" + smsg);
                gui.onMessage(smsg);
            }
        } catch (IOException e) {
            System.out.println("Connection closed or error: " + e.getMessage());
        }
    }
}