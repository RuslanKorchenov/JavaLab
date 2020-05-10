package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    @Size(min=4, max=20, message = "{errors.username.size}")
    private String username;

    @Email(message = "{errors.incorrect.email}")
    @NotNull(message = "{errors.null.email}")
    private String email;

    @Size(min=4, max=40, message = "{errors.password.size}")
    private String password;
}
