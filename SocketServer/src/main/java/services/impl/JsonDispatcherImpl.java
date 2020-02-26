package services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import context.ApplicationContext;
import dto.JsonDto;
import json.JsonTemplate;
import json.authentication.LoginPayload;
import json.command.*;
import json.messages.MessagePayload;
import org.springframework.jdbc.core.JdbcTemplate;
import server.ClientHandler;
import services.interfaces.*;

import java.sql.Connection;

public class JsonDispatcherImpl implements JsonDispatcher {
    private CommandService commandsService;
    private HelpService helpService;
    private MessageService messagesService;
    private UserService usersService;
    private ClientHandler clientHandler;

    public JsonDispatcherImpl(ApplicationContext applicationContext, ClientHandler clientHandler)
            throws IllegalAccessException, ClassNotFoundException {
        commandsService = applicationContext.getComponent(CommandService.class);
        helpService = applicationContext.getComponent(HelpService.class);
        messagesService = applicationContext.getComponent(MessageService.class);
        usersService = applicationContext.getComponent(UserService.class);
        this.clientHandler = clientHandler;
        JdbcTemplate template = applicationContext.getTemplate();

        commandsService.getMessageRepository().setJdbcTemplate(template);
        commandsService.getProductRepository().setJdbcTemplate(template);
        messagesService.getMessageRepository().setJdbcTemplate(template);
        commandsService.getUserRepository().setJdbcTemplate(template);
        usersService.getUserRepository().setJdbcTemplate(template);
    }



    @Override
    public JsonDto dispatch(JsonTemplate jsonTemplate) {
        JsonDto jsonDto = null;
        switch (jsonTemplate.getHeader().get("type")) {
            case "help": {
                jsonDto = helpService.help();
                break;
            }
            case "login": {
                LoginPayload loginPayload = (LoginPayload) jsonTemplate.getPayload();
                jsonDto = usersService.login(loginPayload.getEmail(), loginPayload.getPassword(), clientHandler);
                break;
            }
            case "logout": {
                usersService.logout(clientHandler);
                break;
            }
            case "message": {
                MessagePayload messagePayload = (MessagePayload) jsonTemplate.getPayload();
                jsonDto = messagesService.message(jsonTemplate.getHeader().get("bearer"), messagePayload.getText());
                break;
            }
            case "command": {
                CommandPayload commandPayload = (CommandPayload) jsonTemplate.getPayload();
                DecodedJWT jwt = JWT.decode(jsonTemplate.getHeader().get("bearer"));
                switch (commandPayload.getCommand()) {
                    case "get messages": {
                        CommandListPayload commandListPayload = (CommandListPayload) jsonTemplate.getPayload();
                        jsonDto = commandsService.commandGetMessages(commandListPayload.getSize(), commandListPayload.getPage());
                        break;
                    }
                    case "get products": {
                        CommandProductListPayload commandProductListPayload = (CommandProductListPayload) jsonTemplate.getPayload();
                        jsonDto = commandsService.commandGetProducts();
                        break;
                    }
                    case "add product": {
                        CommandAddProductPayload payload = (CommandAddProductPayload) jsonTemplate.getPayload();
                        if (jwt.getClaims().get("role").asString().equals("ADMIN")) {
                            jsonDto = commandsService.addProduct(payload.getName(), payload.getPrice());
                        } else {
                            jsonDto = commandsService.authorityFail();
                        }
                        break;

                    }
                    case "remove product": {
                        CommandRemoveProductPayload payload = (CommandRemoveProductPayload) jsonTemplate.getPayload();
                        if (jwt.getClaims().get("role").asString().equals("ADMIN")) {
                            jsonDto = commandsService.removeProduct(payload.getId());
                        } else {
                            jsonDto = commandsService.authorityFail();
                        }
                        break;
                    }
                    case "buy": {
                        CommandBuyProductPayload payload = (CommandBuyProductPayload) jsonTemplate.getPayload();
                        jsonDto = commandsService.purchase(payload.getId(), jwt.getClaims().get("sub").asLong());
                        break;
                    }
                }
            }
            break;
        }
        return jsonDto;
    }

}
