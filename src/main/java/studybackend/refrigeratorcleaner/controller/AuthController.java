package studybackend.refrigeratorcleaner.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import studybackend.refrigeratorcleaner.dto.request.*;
import studybackend.refrigeratorcleaner.jwt.dto.request.SocialIdRequest;
import studybackend.refrigeratorcleaner.jwt.dto.response.TokenResponse;
import studybackend.refrigeratorcleaner.jwt.service.JwtService;
import studybackend.refrigeratorcleaner.service.AuthService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/auth/send-email")
    public ResponseEntity<Object> sendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {
        return ResponseEntity.ok(authService.sendEmail(emailRequest));
    }

    @PostMapping("/auth/verify-email")
    public ResponseEntity<Object> verifyEmail(@RequestBody VerifyEmailRequest verifyEmailRequest) {

        authService.verifyEmail(verifyEmailRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/auth/resend-email")
    public ResponseEntity<Object> reSendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {

        return ResponseEntity.ok(authService.sendEmail(emailRequest));

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

    @PostMapping("/auth/reset-password")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {

        authService.resetPassword(resetPasswordRequest);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/auth/token/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request,@RequestBody SocialIdRequest socialIdRequest) {

        String refreshToken = jwtService.extractRefreshToken(request).get();
        authService.logout(refreshToken, socialIdRequest.getSocialId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/auth/token/reissue")
    public ResponseEntity<Object> refresh(HttpServletRequest request, HttpServletResponse response, @RequestBody SocialIdRequest socialIdRequest) {

        String refreshToken = jwtService.extractRefreshToken(request).get();
        TokenResponse tokenResponse = authService.validateToken(refreshToken, socialIdRequest.getSocialId());
        jwtService.setTokens(response, tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/change-nickname")
    public ResponseEntity<Object> changeNickName(@RequestBody ValidateNickNameRequest validateNickNameRequest) {

        authService.changeNickName(validateNickNameRequest);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/change-profile")
    public ResponseEntity<Object> changeProfile(@RequestPart(value = "nickName") NickNameRequest nickNameRequest, @RequestPart(value = "file") MultipartFile multipartFile) throws IOException {

        String updateProfileUrl = authService.updateProfileUrl(multipartFile, nickNameRequest.getNickName());
        Map<String, String> profile = new HashMap<>();
        profile.put("url", updateProfileUrl);

        return ResponseEntity.ok(profile);
    }
}
