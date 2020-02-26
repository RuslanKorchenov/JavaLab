package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import context.ApplicationContext;
import dto.JsonDto;
import dto.Type;
import json.JsonTemplate;
import json.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import services.TokenSecurity;
import services.impl.JsonDispatcherImpl;
import services.interfaces.JsonDispatcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Getter
@NoArgsConstructor
public class ClientHandler extends Thread {

    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private ObjectMapper objectMapper = new ObjectMapper();
    private JsonDispatcher jsonDispatcher;
    private ChatMultiServer chatMultiServer;

    ClientHandler(
            Socket socket,
            ChatMultiServer chatMultiServer,
            ApplicationContext applicationContext) throws ClassNotFoundException, IllegalAccessException {
        this.clientSocket = socket;
        this.chatMultiServer = chatMultiServer;
        jsonDispatcher = new JsonDispatcherImpl(applicationContext, this);
    }

    public void run() {
        try {
            objectMapper = new ObjectMapper();
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JsonTemplate jsonTemplate = objectMapper.readValue(inputLine, JsonTemplate.class);
                if (jsonTemplate.getHeader().get("type").equals("help")
                        || jsonTemplate.getHeader().get("type").equals("login")
                        || TokenSecurity.verify(jsonTemplate.getHeader().get("bearer")) != null);
                JsonDto jsonDto = jsonDispatcher.dispatch(jsonTemplate);
                String res = objectMapper.writeValueAsString(Response.build(jsonDto));
                if (jsonDto.getType().equals(Type.ALL)) {
                    for (ClientHandler client : chatMultiServer.getClients()) {
                        PrintWriter out = new PrintWriter(client.clientSocket.getOutputStream(), true);
                        out.println(res);
                    }
                } else out.println(res);

            }
            in.close();
            clientSocket.close();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}