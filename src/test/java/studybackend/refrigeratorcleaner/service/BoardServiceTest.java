package studybackend.refrigeratorcleaner.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import studybackend.refrigeratorcleaner.dto.BoardDto;
import studybackend.refrigeratorcleaner.entity.Board;
import studybackend.refrigeratorcleaner.entity.BoardContent;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.error.ErrorCode;
import studybackend.refrigeratorcleaner.repository.BoardRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @Test
    @DisplayName("게시판 저장 실패 테스트")
    void saveBoardFailTest() {
        doThrow(new CustomException(ErrorCode.FAILED_TO_SAVE_POST)).when(boardRepository).saveBoard(any(Board.class));

        Board board = Board.builder().build();
        assertThatThrownBy(() -> boardService.saveBoard(board))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.FAILED_TO_SAVE_POST.getCode());
    }

    @Test
    @DisplayName("게시판 내용 저장 실패 테스트")
    void saveBoardContentFailTest() {
        doThrow(new CustomException(ErrorCode.FAILED_TO_MODIFY_POSTS)).when(boardRepository).saveBoardContent(any(BoardContent.class));

        BoardContent boardContent = new BoardContent();
        assertThatThrownBy(() -> boardService.saveBoardContent(boardContent))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.FAILED_TO_MODIFY_POSTS.getCode());
    }

    @Test
    @DisplayName("게시판 업데이트 실패 테스트")
    void updateBoardFailTest() {
        doThrow(new CustomException(ErrorCode.FAILED_TO_MODIFY_POSTS)).when(boardRepository).updateBoard(any(Board.class));

        Board board = Board.builder().build();
        assertThatThrownBy(() -> boardService.updateBoard(board))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.FAILED_TO_MODIFY_POSTS.getCode());
    }

    @Test
    @DisplayName("게시판 DTO 저장 실패 테스트")
    void saveBoardDtoFailTest() {
        doThrow(new CustomException(ErrorCode.FAILED_TO_SAVE_POST)).when(boardRepository).saveBoard(any(Board.class));

        BoardDto boardDto = BoardDto.builder()
                .id(1L)
                .email("test@example.com")
                .nickName("Tester")
                .title("Test Title")
                .texts("Test Texts")
                .likeCount(10)
                .ingredients(Arrays.asList("Ingredient1", "Ingredient2"))
                .build();

        assertThatThrownBy(() -> boardService.saveBoarDto(boardDto))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.FAILED_TO_SAVE_POST.getCode());
    }

    @Test
    @DisplayName("게시판 삭제 실패 테스트")
    void deleteBoardFailTest() {
        Long id = 1L;
        doThrow(new CustomException(ErrorCode.FAILED_TO_LOAD_DELETE_POST)).when(boardRepository).deleteBoard(id);

        assertThatThrownBy(() -> boardService.deleteBoard(id))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.FAILED_TO_LOAD_DELETE_POST.getCode());
    }

    @Test
    @DisplayName("게시판 크기 확인 실패 테스트")
    void boardSizeFailTest() {
        doThrow(new CustomException(ErrorCode.FAILED_TO_LOAD_ALLPOSTS_COUNT)).when(boardRepository).boardSize();

        assertThatThrownBy(() -> boardService.boardSize())
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.FAILED_TO_LOAD_ALLPOSTS_COUNT.getCode());
    }

    @Test
    @DisplayName("최신 게시판 가져오기 실패 테스트")
    void recentBoardFailTest() {
        doThrow(new CustomException(ErrorCode.FAILED_TO_LOAD_TOP)).when(boardRepository).recentBoard();

        assertThatThrownBy(() -> boardService.recentBoard())
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.FAILED_TO_LOAD_TOP.getCode());
    }

    @Test
    @DisplayName("유저 게시판 가져오기 실패 테스트")
    void getUserBoardFailTest() {
        Long id = 1L;
        doThrow(new CustomException(ErrorCode.FAILED_TO_LOAD_DETAIL)).when(boardRepository).getUserBoard(id);

        assertThatThrownBy(() -> boardService.getUserBoard(id))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.FAILED_TO_LOAD_DETAIL.getCode());
    }

    @Test
    @DisplayName("특정 게시판 가져오기 실패 테스트")
    void getSpecificFailTest() {
        Long id = 1L;
        doThrow(new CustomException(ErrorCode.FAILED_TO_LOAD_DETAIL)).when(boardRepository).getSpecific(id);

        assertThatThrownBy(() -> boardService.getSpecific(id))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.FAILED_TO_LOAD_DETAIL.getCode());
    }

    @Test
    @DisplayName("오프셋 기반 게시판 가져오기 실패 테스트")
    void getBoardFailTest() {
        int offset = 0;
        doThrow(new CustomException(ErrorCode.FAILED_TO_LOAD_DETAIL)).when(boardRepository).getBoard(offset);

        assertThatThrownBy(() -> boardService.getBoard(offset))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.FAILED_TO_LOAD_DETAIL.getCode());
    }

    @Test
    @DisplayName("좋아요 수 기반 게시판 정렬 실패 테스트")
    void orderLikeCountFailTest() {
        doThrow(new CustomException(ErrorCode.FAILED_TO_LOAD_TOP)).when(boardRepository).orderLikeCount();

        assertThatThrownBy(() -> boardService.orderLikeCount())
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.FAILED_TO_LOAD_TOP.getCode());
    }

    @Test
    @DisplayName("제목으로 게시판 검색 실패 테스트")
    void searchBoardFailTest() {
        String title = "Test Title";
        doThrow(new CustomException(ErrorCode.NO_SEARCH_RESULTS)).when(boardRepository).searchTitle(title);

        assertThatThrownBy(() -> boardService.searchBoard(title))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.NO_SEARCH_RESULTS.getCode());
    }

    @Test
    @DisplayName("닉네임으로 게시글 리스트 가져오기 실패 테스트")
    void myListFailTest() {
        String email = "test@example.com";
        doThrow(new CustomException(ErrorCode.FAILED_TO_LOAD_MYPOSTS)).when(boardRepository).myList(email);

        assertThatThrownBy(() -> boardService.myList(email))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.FAILED_TO_LOAD_MYPOSTS.getCode());
    }

    @Test
    @DisplayName("특정 게시판 DTO 가져오기 실패 테스트")
    void getBoardDtoFailTest() {
        Long id = 1L;
        doThrow(new CustomException(ErrorCode.FAILED_TO_LOAD_DETAIL)).when(boardRepository).getSpecific(id);

        assertThatThrownBy(() -> boardService.getBoardDto(id))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.FAILED_TO_LOAD_DETAIL.getCode());
    }
}
