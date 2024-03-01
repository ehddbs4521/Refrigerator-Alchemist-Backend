package studybackend.refrigeratorcleaner.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studybackend.refrigeratorcleaner.Entity.Board;
import studybackend.refrigeratorcleaner.Service.BoardService;
import studybackend.refrigeratorcleaner.dto.BoardDto;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class BoardApiController {
    private  final BoardService boardService;

    public Map<String, List<Map<String, Object>>> makeApi(List<Board> boards) {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        for (Board board : boards) {
            Map<String, Object> item = new HashMap<>();
            item.put("Recipe", board.getTexts());
            item.put("nickName", board.getNickName());
            item.put("title", board.getTitle());
            //item.put("email", board.getEmail());
            item.put("likeCount",String.valueOf(board.getLikeCount()));
            List<String> tmp  = new ArrayList<>();
            tmp.add("계란");
            tmp.add("당근");
            tmp.add("쪽파");
            tmp.add("김");
            item.put("ingredients",tmp);
            items.add(item);
        }

        result.put("items", items);
        return result;
    }
    @GetMapping(value = "board/apiTest")
    //@CrossOrigin(origins =  "board/apiTest") //최신순으로 출력
    public  Map<String, List<Map<String, Object>>> getContent(){

        List<Board> boards = boardService.getBoard();
        Map<String, List<Map<String, Object>>> content =  makeApi(boards);
        return  content;
    }
    @GetMapping(value = "board/apiTestLikeCount") // 좋아요 순으로 정렬
    public  Map<String, List<Map<String, Object>>> orderLikeCount(){
        List<Board> boards = boardService.orderLikeCount();
        Map<String, List<Map<String, Object>>> content =  makeApi(boards);
        return  content;
    }
    //제목 검색기능
    @GetMapping(value = "board/searchTitle")
    public Map<String, List<Map<String, Object>>> searchTitle() {
        //검색하고 싶은 제목을 인자에 넣음
        List<Board> boards =  boardService.searchBoard("qwe");
        Map<String, List<Map<String, Object>>> content =  makeApi(boards);
        return content;
    }

    @GetMapping(value = "board/myList")
    public Map<String, List<Map<String, Object>>> myList() {
        String nickName  =  "test";
        List<Board> boards =  boardService.myList(nickName);
        Map<String, List<Map<String, Object>>> content =  makeApi(boards);
        return content;
    }
}
