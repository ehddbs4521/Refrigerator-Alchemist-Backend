package studybackend.refrigeratorcleaner.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import studybackend.refrigeratorcleaner.Entity.Board;
import studybackend.refrigeratorcleaner.Entity.LikeCheck;
import studybackend.refrigeratorcleaner.Service.BoardService;
import studybackend.refrigeratorcleaner.Service.LikeCheckService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LikeApiController {
    private final LikeCheckService lService;
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
    @GetMapping(value = "board/myLike")
    public Map<String, List<Map<String, Object>>> myLike() {
        String clicker = "changeme";
        List<LikeCheck> like =  lService.getMyLikeTitle(clicker);
        String nickName = like.get(0).getNickName();
        String title = like.get(0).getTitle();
        List<Board> board= lService.getMyLikeList(nickName,title);
        Map<String, List<Map<String, Object>>> content =  makeApi(board);
        return content;
    }
}
