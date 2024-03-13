package studybackend.refrigeratorcleaner.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import studybackend.refrigeratorcleaner.error.CustomException;

import static studybackend.refrigeratorcleaner.error.ErrorCode.NOT_AUTHENTICATION_INFO;

@Component
public class SecurityUtil {

    private SecurityUtil() {
    }

     public static Long getCurrentUserId() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new CustomException(NOT_AUTHENTICATION_INFO);
        }

        return Long.parseLong(authentication.getName());
    }
}
