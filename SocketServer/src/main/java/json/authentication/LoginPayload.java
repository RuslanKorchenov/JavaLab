package json.authentication;

import json.AbstractPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginPayload extends AbstractPayload {
    private String email;
    private String password;
}
