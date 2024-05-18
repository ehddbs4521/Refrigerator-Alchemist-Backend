package studybackend.refrigeratorcleaner.auth.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import studybackend.refrigeratorcleaner.dto.request.NickNameRequest;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.error.ErrorCode;
import studybackend.refrigeratorcleaner.repository.UserRepository;
import studybackend.refrigeratorcleaner.service.AuthService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NickNameTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("닉네임 중복 검사(회원가입 시)")
    public void validateNickName_ThrowsCustomException_For_SignUp() throws CustomException {

        NickNameRequest nickNameRequest = new NickNameRequest("hello4521");
        when(userRepository.existsByNickName(nickNameRequest.getNickName())).thenReturn(true);

        assertThatCode(() -> authService.verifyNickName(nickNameRequest.getNickName()))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.EXIST_USER_NICKNAME.getCode());
    }

}
