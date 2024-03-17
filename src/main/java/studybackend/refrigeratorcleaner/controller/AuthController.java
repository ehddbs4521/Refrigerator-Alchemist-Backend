package studybackend.refrigeratorcleaner.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studybackend.refrigeratorcleaner.dto.request.EmailRequest;
import studybackend.refrigeratorcleaner.dto.request.NickNameRequest;
import studybackend.refrigeratorcleaner.dto.request.UserRequest;
import studybackend.refrigeratorcleaner.dto.request.VerifyEmailRequest;
import studybackend.refrigeratorcleaner.jwt.dto.request.SocialIdRequest;
import studybackend.refrigeratorcleaner.jwt.dto.response.TokenResponse;
import studybackend.refrigeratorcleaner.jwt.service.JwtService;
import studybackend.refrigeratorcleaner.service.AuthService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/auth/send-email")
    public ResponseEntity<Object> sendEmail(@RequestBody EmailRequest emailRequest) {

        authService.sendEmail(emailRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/auth/verify-email")
    public ResponseEntity<Object> verifyEmail(@RequestBody VerifyEmailRequest verifyEmailRequest) {

        authService.verifyEmail(verifyEmailRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/auth/resend-email")
    public ResponseEntity<Object> reSendEmail(@RequestBody EmailRequest emailRequest) {

        authService.sendEmail(emailRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/auth/verify-nickname")
    public ResponseEntity<Object> verifyNickName(@RequestBody NickNameRequest nickNameRequest) {

        authService.verifyNickName(nickNameRequest.getNickName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody UserRequest userRequest){

        authService.signup(userRequest);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/auth/token/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request,@RequestBody SocialIdRequest socialIdRequest) {

        String accessToken = jwtService.extractAccessToken(request).get();
        String refreshToken = jwtService.extractRefreshToken(request).get();
        authService.logout(accessToken, refreshToken, socialIdRequest.getSocialId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/auth/token/reissue")
    public ResponseEntity<Object> refresh(HttpServletRequest request, HttpServletResponse response, @RequestBody SocialIdRequest socialIdRequest) {

        String refreshToken = jwtService.extractRefreshToken(request).get();
        TokenResponse tokenResponse = authService.validateToken(refreshToken, socialIdRequest.getSocialId());
        jwtService.setTokens(response, tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
