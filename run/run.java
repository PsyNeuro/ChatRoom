package run;
import server.Server;
import GUI.GUI;
import client.Client;

public class run {
    public static void main(String[] args) {
        GUI.main(null);

        // Start server in a new thread
        new Thread(() -> {
            try {
                Server.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // Start clients in new threads
        new Thread(() -> {
            try {
                Client.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Client.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
