package studybackend.refrigeratorcleaner.service.auth.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import studybackend.refrigeratorcleaner.controller.AuthController;
import studybackend.refrigeratorcleaner.jwt.service.JwtService;
import studybackend.refrigeratorcleaner.repository.UserRepository;
import studybackend.refrigeratorcleaner.service.AuthService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest
public class TokenProviderTest {

    private JwtService jwtService;

    @MockBean
    private AuthController authController;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    @Value("${jwt.secret-key}")
    String secret;

    @Value("${jwt.access-header}")
    String accessHeader;

    @Value("${jwt.refresh-header}")
    String refreshHeader;
    @MockBean
    private SecretKey key;
    @BeforeEach
    void init() {

        jwtService = new JwtService(secret, accessHeader, refreshHeader, userRepository);
        key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    @Test
    @DisplayName("Access토큰을 Bearer로 추출")
    void extractAccessToken() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(accessHeader, "Bearer access");
        Optional<String> token = jwtService.extractAccessToken(request);
        assertThat(token.isPresent()).isTrue();
        assertThat(token.get()).isEqualTo("access");
    }

    @Test
    @DisplayName("Refresh토큰을 Bearer로 추출")
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
    void extractTokenWhenDoesNoTStartWithBearer() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(accessHeader, "Bear Access");
        Optional<String> token = jwtService.extractAccessToken(request);
        assertThat(token.isPresent()).isFalse();
    }

    @Test
    @DisplayName("만료된 토큰이면 false 반환")
    void checkTokenIfExpiredOrNot() {
        String token = generateExpiredAccessToken("1234");

        boolean tokenValid = jwtService.isTokenValid(token);
                assertThat(tokenValid).isFalse();
    }

    @Test
    @DisplayName("유효한 토큰이 아니면 false 반환")
    void checkTokenIfValidSignatureOrNot() {
        String token = "dqdwdwddwd";
        boolean tokenValid = jwtService.isTokenValid(token);
        assertThat(tokenValid).isFalse();
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
