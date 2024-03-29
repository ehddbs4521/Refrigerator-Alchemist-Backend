package studybackend.refrigeratorcleaner.jwt.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.jwt.error.TokenStatus;

import static studybackend.refrigeratorcleaner.error.ErrorCode.*;
import static studybackend.refrigeratorcleaner.error.ErrorCode.ILLEGAL_TOKEN;

@Getter
@RequiredArgsConstructor
@Component
public class JwtErrorHandler {

    public void tokenError(TokenStatus tokenStatus) {
        if (tokenStatus.equals(TokenStatus.WRONG_SIGNATURE)) {
            throw new CustomException(WRONG_SIGNATURE_TOKEN);
        }
        if (tokenStatus.equals(TokenStatus.EXPIRED)) {
            throw new CustomException(EXPIRE_TOKEN);
        }
        if (tokenStatus.equals(TokenStatus.UNSUPPORTED)) {
            throw new CustomException(NOT_SURPPORTED_TOKEN);
        }
        throw new CustomException(ILLEGAL_TOKEN);
    }
}
