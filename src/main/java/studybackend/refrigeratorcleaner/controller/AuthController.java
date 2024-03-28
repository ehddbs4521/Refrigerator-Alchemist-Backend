package studybackend.refrigeratorcleaner.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studybackend.refrigeratorcleaner.dto.request.*;
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

    @PostMapping("/auth/email")
    public ResponseEntity<Object> sendEmail(@RequestBody EmailRequest emailRequest) {

        authService.sendEmail(emailRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/auth/register/authentication/number")
    public ResponseEntity<Object> verifyEmail(@RequestBody VerifyEmailRequest verifyEmailRequest) {

        authService.verifyEmail(verifyEmailRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/auth/reset/password")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {

        authService.resetPassword(resetPasswordRequest);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/auth/register/authentication/nickname")
    public ResponseEntity<Object> verifyNickName(@RequestBody NickNameRequest nickNameRequest) {

        authService.verifyNickName(nickNameRequest.getNickName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Object> signup(@Valid @RequestBody UserRequest userRequest){

        authService.signup(userRequest);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/token/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {

        String accessToken = jwtService.extractAccessToken(request).get();
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String socialId = principal.getUsername();
        authService.logout(accessToken, socialId);
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/reissue")
    public ResponseEntity<Object> refresh(HttpServletRequest request, HttpServletResponse response) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String socialId = principal.getUsername();
        String accessToken = jwtService.extractAccessToken(request).get();
        String refreshToken = jwtService.extractRefreshToken(request).get();
        TokenResponse tokenResponse = authService.validateToken(accessToken,refreshToken, socialId);
        jwtService.setTokens(response, tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
