package programs.server;

import application.ApplicationServer;
import context.ApplicationContext;
import context.ApplicationContextImpl;


public class Server {

    public static void main(String[] args) {
        ApplicationServer serverApplication = new ApplicationServer(args);
        serverApplication.startRunning();
    }
}
