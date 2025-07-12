package run;
import server.Server;
import GUI_S.GUI_S;
import GUI_C.GUI_C;
import client.Client;

public class run {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> GUI_S.main(null));

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

        javax.swing.SwingUtilities.invokeLater(() -> GUI_C.main(null));

        new Thread(() -> {
            try {
                Client.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        javax.swing.SwingUtilities.invokeLater(() -> GUI_C.main(null));
        
    }
}
