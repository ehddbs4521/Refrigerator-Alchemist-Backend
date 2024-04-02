package studybackend.refrigeratorcleaner.login.handler;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;
import studybackend.refrigeratorcleaner.entity.User;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.jwt.service.JwtService;
import studybackend.refrigeratorcleaner.redis.entity.RefreshToken;
import studybackend.refrigeratorcleaner.redis.repository.RefreshTokenRepository;
import studybackend.refrigeratorcleaner.repository.UserRepository;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;

import static studybackend.refrigeratorcleaner.error.CustomServletException.sendJsonError;
import static studybackend.refrigeratorcleaner.error.ErrorCode.NOT_EXIST_USER_SOCIALID;


@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        try {
            String socialId = extractUsername(authentication);
            User userInfo = userRepository.findBySocialId(socialId)
                    .orElseThrow(() -> new CustomException(NOT_EXIST_USER_SOCIALID));
            String email = userInfo.getEmail();

            String accessToken = jwtService.generateAccessToken(socialId);
            String refreshToken = jwtService.generateRefreshToken(socialId);

            Optional<RefreshToken> token = refreshTokenRepository.findBySocialId(socialId);

            if (token.isEmpty()) {
                refreshTokenRepository.save(new RefreshToken(socialId, refreshToken));
            } else {
                token.get().updateRefreshToken(refreshToken);
                refreshTokenRepository.save(token.get());
            }

            log.info("로그인에 성공하였습니다. 이메일 : {}", email);
            log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);
            log.info("로그인에 성공하였습니다. RefreshToken : {}", refreshToken);

            response.setHeader("Authorization-Access", "Bearer " + accessToken);
            response.setHeader("Authorization-Refresh", "Bearer " + refreshToken);
            response.setHeader("email", email);
            response.setHeader("nickName", URLEncoder.encode(userInfo.getNickName(), "utf-8"));
            response.setHeader("socialId", socialId);
            response.setStatus(HttpStatus.OK.value());
        } catch (CustomException e) {
            sendJsonError(response, e.getErrorCode().getStatus().value(), e.getErrorCode().getCode());
        }

    }


    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

}
