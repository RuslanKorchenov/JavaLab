package server;


import context.ApplicationContext;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@NoArgsConstructor
public class ChatMultiServer {

    private List<ClientHandler> clients;
    private ApplicationContext applicationContext;

    public ChatMultiServer(ApplicationContext applicationContext) throws ClassNotFoundException, IllegalAccessException {
        clients = new CopyOnWriteArrayList<>();
        this.applicationContext = applicationContext;
    }

    public void start(int port) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        while (true) {
            try {
                ClientHandler clientHandler = new ClientHandler(serverSocket.accept(), this, applicationContext);

                clientHandler.start();
            } catch (IOException | IllegalAccessException | ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
