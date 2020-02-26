package json.status;

import json.AbstractPayload;

public class SuccessPayload extends AbstractPayload {
    private final String text = "Success!";

    public String getText() {
        return text;
    }
}
