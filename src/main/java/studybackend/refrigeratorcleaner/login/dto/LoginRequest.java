package studybackend.refrigeratorcleaner.login.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String socialType;
}
