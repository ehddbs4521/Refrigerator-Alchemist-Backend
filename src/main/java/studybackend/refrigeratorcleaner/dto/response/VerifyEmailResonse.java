package studybackend.refrigeratorcleaner.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyEmailResonse {

    private String randomNum;
    private LocalDateTime createTime;
    private LocalDateTime expireTime;
}
