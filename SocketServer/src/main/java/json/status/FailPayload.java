package json.status;

import json.AbstractPayload;

public class FailPayload extends AbstractPayload {
    private final String text = "Failed!";

    public String getText() {
        return text;
    }
}
