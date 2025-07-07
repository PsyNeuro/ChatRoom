package server;

class NotifierThread implements Runnable {
    private String message;
    
    public NotifierThread(String message) {
        this.message = message;

    }

    @Override
    public void run() {
        Server.notifyListeners(message);
    }
}
