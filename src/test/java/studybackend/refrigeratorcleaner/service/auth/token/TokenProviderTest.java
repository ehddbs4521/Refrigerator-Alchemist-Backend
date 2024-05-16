package studybackend.refrigeratorcleaner.service.auth.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import studybackend.refrigeratorcleaner.controller.AuthController;
import studybackend.refrigeratorcleaner.jwt.error.TokenStatus;
import studybackend.refrigeratorcleaner.jwt.service.JwtService;
import studybackend.refrigeratorcleaner.repository.UserRepository;
import studybackend.refrigeratorcleaner.service.AuthService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static studybackend.refrigeratorcleaner.jwt.error.TokenStatus.EXPIRED;
import static studybackend.refrigeratorcleaner.jwt.error.TokenStatus.WRONG_SIGNATURE;

@WebMvcTest(AuthController.class)
public class TokenProviderTest {

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    private String secret="Zm9vYmFyYmF6cXV4eXo=";

    @Value("${jwt.access-header}")
    private String accessHeader;

    @Value("${jwt.refresh-header}")
    private String refreshHeader;

    private SecretKey key;

    @BeforeEach
    void init() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        byte[] keyBytes = key.getEncoded();
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);
        jwtService = new JwtService(base64Key, accessHeader, refreshHeader, userRepository);

    }

    @Test
    @DisplayName("Access 토큰을 Bearer로 추출")
    void extractAccessToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(accessHeader, "Bearer access");
        Optional<String> token = jwtService.extractAccessToken(request);
        assertThat(token.isPresent()).isTrue();
        assertThat(token.get()).isEqualTo("access");
    }
    @Test
    @DisplayName("Refresh 토큰을 Bearer로 추출")
    void extractRefreshToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(refreshHeader, "Bearer refresh");
        Optional<String> token = jwtService.extractRefreshToken(request);
        assertThat(token.isPresent()).isTrue();
        assertThat(token.get()).isEqualTo("refresh");
    }

    @Test
    @DisplayName("헤더에 토큰이 존재하지 않으면 empty이다.")
    void extractTokenWhenDoesNotExist() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(accessHeader, "");
        Optional<String> token = jwtService.extractAccessToken(request);
        assertThat(token.isPresent()).isFalse();
    }

    @Test
    @DisplayName("토큰이 Bearer로 시작하지 않으면 empty이다.")
    void extractTokenWhenDoesNotStartWithBearer() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(accessHeader, "Bear Access");
        Optional<String> token = jwtService.extractAccessToken(request);
        assertThat(token.isPresent()).isFalse();
    }

    @Test
    @DisplayName("만료된 토큰이면 false 반환")
    void checkTokenIfExpiredOrNot() {
        String token = generateExpiredAccessToken("1234");

        TokenStatus tokenValid = jwtService.isTokenValid(token);
        assertThat(tokenValid).isEqualTo(EXPIRED);
    }

    @Test
    @DisplayName("유효한 토큰이 아니면 false 반환")
    void checkTokenIfValidSignatureOrNot() {
        String token = "dqdwdwddwd";
        TokenStatus tokenValid = jwtService.isTokenValid(token);
        assertThat(tokenValid).isEqualTo(WRONG_SIGNATURE);
    }

    @Test
    @DisplayName("토큰의 socialId와 사용자의 socialId가 같은지 확인")
    void checkTokenSocialIdEqualsUserSocialId() {
        String token = jwtService.generateAccessToken("hello");
        String socialId = jwtService.extractSocialId(token).get();
        assertThat(socialId).isEqualTo("hello");
    }

    public String generateExpiredAccessToken(String socialId) {
        long now = new Date().getTime();
        Date date = new Date(now - 2);

        return Jwts.builder()
                .setSubject("ACCESS_TOKEN_SUBJECT")
                .claim("socialId", socialId)
                .setExpiration(date)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

}
