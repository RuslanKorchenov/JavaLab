package dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class JsonDto {
    String name;
    Type type;

    public JsonDto(String name, Type type) {
        this.name = name;
        this.type = type;
    }

}
