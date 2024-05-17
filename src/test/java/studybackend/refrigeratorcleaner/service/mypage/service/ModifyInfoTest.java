package studybackend.refrigeratorcleaner.service.mypage.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import studybackend.refrigeratorcleaner.dto.request.ValidateNickNameRequest;
import studybackend.refrigeratorcleaner.entity.User;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.error.ErrorCode;
import studybackend.refrigeratorcleaner.repository.UserRepository;
import studybackend.refrigeratorcleaner.service.AuthService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ModifyInfoTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("닉네임 변경시 기존 닉네임이 존재하지 않으면 변경 불가능")
    void canNotChangeNickNameWhenExistsNickName() {
        ValidateNickNameRequest validateNickNameRequest = new ValidateNickNameRequest("hello123", "bye123");
        when(userRepository.findByNickName("hello123")).thenReturn(Optional.empty());
        when(userRepository.findByNickName("bye123")).thenReturn(Optional.of(new User()));
        assertThatCode(() -> authService.changeNickName(validateNickNameRequest))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.NOT_EXIST_USER_NICKNAME.getCode());
    }

    @Test
    @DisplayName("변경 할 닉네임이 존재 할 시 변경 불가능")
    void canNotChangeNickNameWhenExistsChangeNickNameInDB() {
        ValidateNickNameRequest validateNickNameRequest = new ValidateNickNameRequest("hello123", "bye123");
        when(userRepository.findByNickName("hello123")).thenReturn(Optional.of(new User()));
        when(userRepository.findByNickName("bye123")).thenReturn(Optional.of(new User()));
        assertThatCode(() -> authService.changeNickName(validateNickNameRequest))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.EXIST_USER_NICKNAME.getCode());
    }

    @Test
    @DisplayName("닉네임이 존재하지 않을 시 프로필 변경 불가능")
    void canNotChangeProfileWhenExistsChangeNickNameInDB() {
        MultipartFile mockMultipartFile = new MockMultipartFile("file", "filename.txt", "text/plain","file content".getBytes());
        when(userRepository.findByNickName("hello123")).thenReturn(Optional.empty());
        assertThatCode(() -> authService.updateProfileUrl(mockMultipartFile,"hello123"))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.NOT_EXIST_USER_NICKNAME.getCode());
    }


}
