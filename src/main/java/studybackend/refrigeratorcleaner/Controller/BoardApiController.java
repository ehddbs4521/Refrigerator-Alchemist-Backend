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
    public Map<String,List<String>>  makeApi (Map<String,List<String>> content ,List<Board> boards){
        List<String> email = new ArrayList<>();
        List<String> nickName = new ArrayList<>();
        List<String> texts = new ArrayList<>();
        List<String> title = new ArrayList<>();
        for (int i = 0; i<boards.size();i++){
            email.add(boards.get(i).getEmail());
            nickName.add(boards.get(i).getNickName());
            texts.add(boards.get(i).getTexts());
            title.add(boards.get(i).getTitle());
        }
        content.put("email",email);
        content.put("nickName",nickName);
        content.put("texts",texts);
        content.put("title",title);
        return content;
    }
    @GetMapping(value = "board/apiTest")
    public  Map<String,List<String>> getContent(){
        Map<String, List<String>> content = new HashMap<>();
        List<Board> boards = boardService.getBoard();
        makeApi(content,boards);
        return  content;
    }
    //제목 검색기능
    @GetMapping(value = "board/searchTitle")
    public Map<String,List<String>> searchTitle() {
        Map<String, List<String>> content = new HashMap<>();
        //검색하고 싶은 제목을 인자에 넣음
        List<Board> boards =  boardService.searchBoard("qwe");
        makeApi(content,boards);
        return content;
    }
}
