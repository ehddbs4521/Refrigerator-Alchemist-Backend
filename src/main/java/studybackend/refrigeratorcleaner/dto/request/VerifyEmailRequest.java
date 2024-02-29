package studybackend.refrigeratorcleaner.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyEmailRequest {

    private String email;
    private String socialType;
    private String randomNum;
    private String inputNum;
    private LocalDateTime sendTime;
    private LocalDateTime expireTime;
}
