package json.messages;

import json.AbstractPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SingleMessagePayload extends AbstractPayload {
    private String text;
    private String nickname;
    private Date date;
}

