package studybackend.refrigeratorcleaner.Controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import studybackend.refrigeratorcleaner.Entity.Board;
import studybackend.refrigeratorcleaner.Entity.BoardContent;
import studybackend.refrigeratorcleaner.Form.LoginForm;
import studybackend.refrigeratorcleaner.Service.BoardService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {
    private final BoardService bService ;
    @GetMapping(value = "/board")
    public  String Board(HttpServletRequest request) {
        return "home";
    }
    @PostMapping(value =  "/board")
    public String  postMainPage(@Valid LoginForm form , BindingResult result, HttpServletRequest request, HttpServletResponse response)
    {
        return "redirect:/board";
    }
    @GetMapping(value ="/write")
    public String writeContent(HttpServletRequest request) {return "writeContent";}

    @PostMapping(value = "/content/write")//작성한 게시글을 저장
    public String postWriteContent(Board board, BoardContent boardContent) {
        System.out.println("good");
//        Board newBoard = Board.builder()
//                .email("test@naver.com")
//                .nickName("test")
//                .likeCount(0)
//                .title(board.getTitle())  // 기존 board의 title을 가져옴
//                .build();
        board.setEmail("test@naver.com");
        boardContent.setEmail("test@naver.com");
        board.setLikeCount(0);
        board.setNickName("test");
        boardContent.setNickName("test");
        bService.saveBoard(board);
        bService.saveBoardContent(boardContent);
        return "redirect:/board";
    }
}
