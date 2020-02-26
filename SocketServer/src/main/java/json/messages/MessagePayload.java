package json.messages;

import json.AbstractPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessagePayload extends AbstractPayload {
    private String text;
}
