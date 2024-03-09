package studybackend.refrigeratorcleaner.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studybackend.refrigeratorcleaner.dto.request.EmailRequest;
import studybackend.refrigeratorcleaner.dto.request.ResetPasswordRequest;
import studybackend.refrigeratorcleaner.dto.request.UserRequest;
import studybackend.refrigeratorcleaner.dto.request.VerifyEmailRequest;
import studybackend.refrigeratorcleaner.jwt.dto.request.ReIssueRequest;
import studybackend.refrigeratorcleaner.jwt.service.JwtService;
import studybackend.refrigeratorcleaner.service.AuthService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/send-email")
    public ResponseEntity<Object> sendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {
        return ResponseEntity.ok(authService.sendEmail(emailRequest));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<Object> verifyEmail(@RequestBody VerifyEmailRequest verifyEmailRequest) {

        authService.verifyEmail(verifyEmailRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/resend-email")
    public ResponseEntity<Object> reSendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {

        return ResponseEntity.ok(authService.sendEmail(emailRequest));

    }

    @PostMapping("/verify-nickname")
    public ResponseEntity<Object> verifyNickName(@RequestBody HashMap<String, String> nickName) {

        authService.verifyNickName(nickName.get("nickName"));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody UserRequest userRequest) throws IOException {

        authService.signup(userRequest);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {

        authService.resetPassword(resetPasswordRequest);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/token/logout")
    public ResponseEntity<Object> logout(@RequestBody HashMap<String,String> accessToken) {

        jwtService.removeRefreshToken(accessToken.get("accessToken"));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<Object> refresh(@RequestBody ReIssueRequest reIssueRequest) {

        String token = jwtService.validRefreshToken(reIssueRequest);
        Map<String, String> accessToken = new HashMap<>();
        accessToken.put("accessToken", token);

        return ResponseEntity.ok(accessToken);

    }
}
