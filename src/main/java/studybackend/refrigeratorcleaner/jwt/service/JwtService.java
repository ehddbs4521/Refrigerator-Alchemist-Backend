package studybackend.refrigeratorcleaner.jwt.service;


import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import studybackend.refrigeratorcleaner.jwt.error.TokenStatus;
import studybackend.refrigeratorcleaner.repository.UserRepository;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Getter
@Slf4j
public class JwtService {

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String BEARER = "Bearer ";
    private static final String EMAIL_CLAIM = "email";
    private static final String SOCIAL_TYPE = "socialType";
    private static final String SOCIAL_ID = "socialId";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 2;            // 유효기간 2시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14;  // 유효기간 14일

    private String accessHeader;
    private String refreshHeader;
    private final SecretKey key;
    private final UserRepository userRepository;

    public JwtService(@Value("${jwt.secret-key}") String secret,
                      @Value("${jwt.access-header}") String accessHeader,
                      @Value("${jwt.refresh-header}") String refreshHeader,
                      UserRepository userRepository) {
        this.accessHeader = accessHeader;
        this.refreshHeader = refreshHeader;
        byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(UTF_8));
        this.key = new SecretKeySpec(keyBytes, "HmacSHA512");
        this.userRepository = userRepository;
    }
    public String generateAccessToken(String socialId) {
        long now = (new Date()).getTime();

        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .claim(SOCIAL_ID, socialId)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String socialId) {

        long now = (new Date()).getTime();

        Date refreshTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        return  Jwts.builder()
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .claim(SOCIAL_ID, socialId)
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public Date extractTime(String accessToken) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .getExpiration();
    }

    public Optional<String> extractSocialId(String token) {

        return Optional.ofNullable(Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(SOCIAL_ID, String.class));

    }

    public TokenStatus isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return TokenStatus.SUCCESS;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            return TokenStatus.WRONG_SIGNATURE;
        } catch (ExpiredJwtException e) {
            return TokenStatus.EXPIRED;
        } catch (UnsupportedJwtException e) {
            return TokenStatus.UNSUPPORTED;
        } catch (IllegalArgumentException e) {
            return TokenStatus.ILLEGAL_TOKEN;
        }
    }

    public void setTokens(HttpServletResponse response, String accessToken, String refreshToken) {

        response.setHeader(accessHeader, accessToken);
        response.setHeader(refreshHeader, refreshToken);

    }
}
