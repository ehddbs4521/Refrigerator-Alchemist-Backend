package studybackend.refrigeratorcleaner.login.handler;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import studybackend.refrigeratorcleaner.entity.Token;
import studybackend.refrigeratorcleaner.entity.User;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.jwt.service.JwtService;
import studybackend.refrigeratorcleaner.repository.TokenRepository;
import studybackend.refrigeratorcleaner.repository.UserRepository;

import static studybackend.refrigeratorcleaner.error.ErrorCode.NO_EXIST_USER_SOCIALID;
import static studybackend.refrigeratorcleaner.error.ErrorCode.NO_EXIST_USER_TOKEN;


@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        String socialId = extractUsername(authentication);
        User userInfo = userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new CustomException(NO_EXIST_USER_SOCIALID));
        String email = userInfo.getEmail();

        String accessToken = jwtService.generateAccessToken(socialId);
        String refreshToken = jwtService.generateRefreshToken(socialId);

        userRepository.findBySocialId(socialId)
                .ifPresentOrElse(user -> {
                    Token token = user.getToken(); // User로부터 Token을 가져옵니다.
                    if (token != null) {
                        token.updateTokens(accessToken, refreshToken); // Token 엔터티를 업데이트합니다.
                        tokenRepository.saveAndFlush(token); // 변경된 Token 엔터티를 저장하고 flush합니다.
                    } else {
                        throw new CustomException(NO_EXIST_USER_TOKEN); // 해당 User에 Token이 없으면 예외를 던집니다.
                    }
                }, () -> { throw new CustomException(NO_EXIST_USER_SOCIALID); }); // 해당 socialId를 가진 User가 없으면 예외를 던집니다.


        log.info("로그인에 성공하였습니다. 이메일 : {}", email);
        log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);

        response.setHeader("Authorization-Access", accessToken);
        response.addCookie(createCookie("Authorization-Refresh", refreshToken));
        response.setStatus(HttpStatus.OK.value());

    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(1000 * 60 * 60 * 24 * 14);
        cookie.setHttpOnly(true);

        return cookie;
    }
}
