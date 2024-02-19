package studybackend.refrigeratorcleaner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDto {

    private String email;
    private String nickName;
    private String password;


}
