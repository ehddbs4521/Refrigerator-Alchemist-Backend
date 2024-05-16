package studybackend.refrigeratorcleaner.service.auth.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import studybackend.refrigeratorcleaner.dto.request.EmailRequest;
import studybackend.refrigeratorcleaner.dto.request.VerifyEmailRequest;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.error.ErrorCode;
import studybackend.refrigeratorcleaner.redis.entity.EmailAuthentication;
import studybackend.refrigeratorcleaner.redis.repository.EmailAuthenticationRepository;
import studybackend.refrigeratorcleaner.repository.UserRepository;
import studybackend.refrigeratorcleaner.service.AuthService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailAuthenticationRepository emailAuthenticationRepository;

    @InjectMocks
    private AuthService authService;


    @Test
    @DisplayName("이메일 인증번호 전송(회원가입 시)")
    public void validateEmail_ThrowsCustomException_For_SignUp() throws CustomException {

        EmailRequest emailRequest = new EmailRequest("test@example.com", "sign-up", "Refrigerator-Cleaner");
        when(userRepository.existsByEmailAndSocialType(emailRequest.getEmail(), emailRequest.getSocialType())).thenReturn(true);

        Throwable throwable = catchThrowable(() -> authService.validateEmail(emailRequest));

        assertThat(throwable)
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.EXIST_USER_EMAIL_SOCIALTYPE.getCode());
    }

    @Test
    @DisplayName("이메일 인증번호 전송(비밀번호 재설정 시)")
    public void validateEmail_ThrowsCustomException_For_Reset_Password() throws CustomException {

        EmailRequest emailRequest = new EmailRequest("test@example.com", "reset-password", "Refrigerator-Cleaner");
        when(userRepository.existsByEmailAndSocialType(emailRequest.getEmail(), emailRequest.getSocialType())).thenReturn(false);

        Throwable throwable = catchThrowable(() -> authService.validateEmail(emailRequest));

        assertThat(throwable)
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.NOT_EXIST_USER_EMAIL_SOCIALTYPE.getCode());
    }


    @Test
    @DisplayName("인증번호 확인(인증번호 잘못 입력 시)")
    public void validateEmail_ThrowsCustomException_For_Authentication_Code() throws CustomException {

        VerifyEmailRequest verifyEmailRequest = new VerifyEmailRequest("test@example.com","sign-up","1235","Refrigerator-Cleaner");
        EmailAuthentication emailAuthentication = new EmailAuthentication("1", "1234", 1L);

        when(emailAuthenticationRepository.findById(anyString()))
                .thenReturn(Optional.of(emailAuthentication));

        Throwable throwable = catchThrowable(() -> authService.verifyEmail(verifyEmailRequest));

        assertThat(throwable)
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.WRONG_CERTIFICATION_NUMBER.getCode());
    }

    @Test
    @DisplayName("인증번호 확인(인증번호 만료 시)")
    public void validateEmail_ThrowsCustomException_For_Expire_Authentication_Code() throws CustomException {

        VerifyEmailRequest verifyEmailRequest = new VerifyEmailRequest("test@example.com","sign-up","1234","Refrigerator-Cleaner");
        EmailAuthentication emailAuthentication = new EmailAuthentication("1", "1234", 1L);

        when(emailAuthenticationRepository.findById(anyString()))
                .thenReturn(Optional.of(emailAuthentication));

        Throwable throwable = catchThrowable(() -> authService.verifyEmail(verifyEmailRequest));

        assertThat(throwable)
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.EXPIRE_CERTIFICATION_NUMBER.getCode());
    }


}
