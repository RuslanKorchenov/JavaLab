package json.authentication;

import json.AbstractPayload;


public class LoginStatusPayload extends AbstractPayload {
    private String text = "Welcome!";

    public LoginStatusPayload() {
    }

    public String getText() {
        return text;
    }
}
