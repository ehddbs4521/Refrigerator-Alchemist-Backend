package studybackend.refrigeratorcleaner.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import studybackend.refrigeratorcleaner.Entity.Board;
import studybackend.refrigeratorcleaner.Service.BoardService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoardApiController {
    private  final BoardService boardService;
    @GetMapping("/api/boards")
    public Map<String, List<Map<String, String>>> makeApi(List<Board> boards) {
        Map<String, List<Map<String, String>>> result = new HashMap<>();
        List<Map<String, String>> items = new ArrayList<>();

        for (Board board : boards) {
            Map<String, String> item = new HashMap<>();
            item.put("text", board.getTexts());
            item.put("nickName", board.getNickName());
            item.put("title", board.getTitle());
            item.put("email", board.getEmail());
            item.put("likeCount",String.valueOf(board.getLikeCount()));
            items.add(item);
        }

        result.put("items", items);
        return result;
    }
    @GetMapping(value = "board/apiTest") //최신순으로 출력
    public  Map<String, List<Map<String, String>>> getContent(){

        List<Board> boards = boardService.getBoard();
        Map<String, List<Map<String, String>>> content =  makeApi(boards);
        return  content;
    }
    @GetMapping(value = "board/apiTestLikeCount") // 좋아요 순으로 정렬
    public  Map<String, List<Map<String, String>>> orderLikeCount(){
        List<Board> boards = boardService.orderLikeCount();
        Map<String, List<Map<String, String>>> content =  makeApi(boards);
        return  content;
    }
    //제목 검색기능
    @GetMapping(value = "board/searchTitle")
    public Map<String, List<Map<String, String>>> searchTitle() {

        //검색하고 싶은 제목을 인자에 넣음
        List<Board> boards =  boardService.searchBoard("qwe");
        Map<String, List<Map<String, String>>> content =  makeApi(boards);
        return content;
    }
}
