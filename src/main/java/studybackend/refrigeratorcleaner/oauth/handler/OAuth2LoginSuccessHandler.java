package studybackend.refrigeratorcleaner.oauth.handler;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import studybackend.refrigeratorcleaner.dto.Role;
import studybackend.refrigeratorcleaner.entity.Token;
import studybackend.refrigeratorcleaner.entity.User;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.jwt.service.JwtService;
import studybackend.refrigeratorcleaner.oauth.dto.CustomOAuth2User;
import studybackend.refrigeratorcleaner.repository.TokenRepository;
import studybackend.refrigeratorcleaner.repository.UserRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static studybackend.refrigeratorcleaner.error.ErrorCode.NO_EXIST_USER_SOCIALID;
import static studybackend.refrigeratorcleaner.error.ErrorCode.NO_EXIST_USER_TOKEN;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            if(oAuth2User.getRole() == Role.GUEST.getKey()) {
                User user = userRepository.findBySocialId(oAuth2User.getSocialId()).orElseThrow(() -> new CustomException(NO_EXIST_USER_SOCIALID));
                Token token = user.getToken();
                if (token == null) {
                    throw new CustomException(NO_EXIST_USER_TOKEN);
                }
                String accessToken = jwtService.generateAccessToken(oAuth2User.getSocialId());
                String refreshToken = jwtService.generateRefreshToken(oAuth2User.getSocialId());
                token.updateTokens(accessToken, refreshToken);
                tokenRepository.save(token);
                String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/login-success")
                        .queryParam("email",oAuth2User.getEmail())
                        .queryParam("socialType",oAuth2User.getSocialType())
                        .queryParam("socialId",oAuth2User.getSocialId())
                        .queryParam("accessToken", accessToken)
                        .build()
                        .encode(StandardCharsets.UTF_8)
                        .toUriString();

                response.setHeader("Authorization-Access", accessToken);
                response.addCookie(createCookie("Authorization-Refresh", refreshToken));
                response.setStatus(HttpStatus.OK.value());


            } else {
                loginSuccess(request,response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
            }
        } catch (Exception e) {
            throw e;
        }

    }

    private void loginSuccess(HttpServletRequest request,HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {

        String accessToken = jwtService.generateAccessToken(oAuth2User.getSocialId());
        String refreshToken = jwtService.generateRefreshToken(oAuth2User.getSocialId());
        jwtService.updateTokens(oAuth2User.getSocialId(), accessToken, refreshToken);

        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/login-success")
                .queryParam("email",oAuth2User.getEmail())
                .queryParam("socialType",oAuth2User.getSocialType())
                .queryParam("socialId",oAuth2User.getSocialId())
                .queryParam("accessToken", accessToken)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        response.setHeader("Authorization-Access", accessToken);
        response.addCookie(createCookie("Authorization-Refresh", refreshToken));
        response.setStatus(HttpStatus.OK.value());

    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(1000 * 60 * 60 * 24 * 14);
        cookie.setHttpOnly(true);

        return cookie;
    }
}
