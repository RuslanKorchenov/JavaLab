package application;

import com.beust.jcommander.JCommander;
import context.ApplicationContext;
import context.ApplicationContextImpl;
import context.Component;
import jcommanderProperties.ServerProperties;
import server.ChatMultiServer;

public class ApplicationServer {
    private String[] args;
    private ServerProperties serverProperties;

    public ApplicationServer(String[] args) {
        this.args = args;
        serverProperties = new ServerProperties();
        JCommander.newBuilder()
                .addObject(serverProperties)
                .args(args)
                .build();
    }

    public void startRunning() {
        try {
            ApplicationContext applicationContext = new ApplicationContextImpl();
            applicationContext.createTemplate(serverProperties.getProperties());
            applicationContext.createComponents();
            System.out.println("——————————————————————");
            System.out.println("Server started!");
            ChatMultiServer chatMultiServer = new ChatMultiServer(applicationContext);
            chatMultiServer.start(serverProperties.getPort());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
