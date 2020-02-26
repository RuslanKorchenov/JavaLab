package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagesDto extends JsonDto {
    private List<MessageDto> messages;

    public MessagesDto(Type type, List<MessageDto> messages, String name) {
        super(name, type);
        this.messages = messages;
    }

}
