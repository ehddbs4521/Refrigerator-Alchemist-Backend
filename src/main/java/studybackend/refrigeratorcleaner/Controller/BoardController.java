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
import org.springframework.web.bind.annotation.RequestBody;
import studybackend.refrigeratorcleaner.Entity.Board;
import studybackend.refrigeratorcleaner.Entity.BoardContent;
import studybackend.refrigeratorcleaner.Entity.LikeCheck;
import studybackend.refrigeratorcleaner.Form.LoginForm;
import studybackend.refrigeratorcleaner.Service.BoardService;
import studybackend.refrigeratorcleaner.Service.LikeCheckService;
import studybackend.refrigeratorcleaner.dto.BoardDto;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {
    private final BoardService bService ;
    private final LikeCheckService lService;
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
        board.setEmail("delTest@naver.com");
        boardContent.setEmail("delTest@naver.com");
        board.setLikeCount(0);
        board.setNickName("delTest");
        boardContent.setNickName("delTest");
        bService.saveBoard(board);
        bService.saveBoardContent(boardContent);
        return "redirect:/board";
    }
    @GetMapping (value = "board/likeApi") // 좋아요 누르기
    public String clickLike() {

        String nickName = "test";
        String title = "jxckmnjv";
        String clickerName = "changeme";
        //좋아요 누르기 전 '좋아요' 중복클릭 했는지 체크
        if(lService.verifyDoubleLike(nickName,title,clickerName).size()==0){
            //좋아요 클릭할 게시물의 제목으로 검색해 가져옴
            List<Board> boards =  bService.searchBoard(title);
            boards.get(0).setLikeCount(boards.get(0).getLikeCount()+1); //좋아요 1증가
            bService.updateBoard(boards.get(0)); //변경된 좋아요 수를 db에 반영

            LikeCheck l = new LikeCheck();
            l.setNickName(nickName);
            l.setTitle(title);
            l.setClickerName(clickerName);
            lService.logLikeCheck(l);
        }
        return "home";
    }
}
