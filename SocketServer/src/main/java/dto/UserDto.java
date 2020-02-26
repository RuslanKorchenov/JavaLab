package dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto extends JsonDto {
    private String nickname;

    private Long id;
    private String token;

    public UserDto(Type type, String nickname, Long id, String token) {
        super("user", type);
        this.nickname = nickname;

        this.id = id;
        this.token = token;
    }
}
