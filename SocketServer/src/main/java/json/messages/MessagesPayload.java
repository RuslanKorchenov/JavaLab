package json.messages;

import json.AbstractPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessagesPayload extends AbstractPayload {
    private List<SingleMessagePayload> messages;

}
