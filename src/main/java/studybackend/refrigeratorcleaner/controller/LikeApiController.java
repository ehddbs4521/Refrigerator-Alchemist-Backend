package studybackend.refrigeratorcleaner.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final BoardService bService;
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
    //    @GetMapping(value = "board/myLike")
//    public Map<String, List<Map<String, Object>>> myLike() {
//        String clicker = "changeme";
//        List<LikeCheck> like =  lService.getMyLikeTitle(clicker);
//        String nickName = like.get(0).getNickName();
//        String title = like.get(0).getTitle();
//        List<Board> board= lService.getMyLikeList(nickName,title);
//        Map<String, List<Map<String, Object>>> content =  makeApi(board);
//        return content;
//    }
    @PostMapping ("board/islike")
    public List<String> boardLike(@RequestBody String id){
        //

        System.out.println("islike 들어옴" + id);

        List<String> answer = new ArrayList<>();
        List<LikeCheck> likeChecks = lService.getMyLikeTitle(id);
        for( LikeCheck likeCheck:likeChecks){
            answer.add(likeCheck.getBoardId());
        }
        return answer;
    }
    @PostMapping ("board/like")
    public void onClickLike(HttpServletRequest request,@RequestBody Map<String,String> formData){
        //,@RequestParam Map<String,String> formData
        String nickName= formData.get("nickName");
        String postId = formData.get("postId");
        if( lService.verifyDoubleLike(nickName,postId).size() ==0){
            System.out.println(nickName+"이"+postId+"에 좋아요 누름");
            LikeCheck likeCheck = LikeCheck.builder().
                    nickName(nickName).boardId(postId)
                    .build();
            lService.logLikeCheck(likeCheck);
            Board b = bService.getUserBoard(Long.valueOf(postId));
            b.onLikeCnt();
            bService.updateBoard(b);
        }else{
            System.out.println("이미 누른 좋아요");
        }
    }

    @PostMapping("board/dislike")
    public  void offClickLike(HttpServletRequest request, @RequestBody Map<String,String> formData){
        String nickName= formData.get("nickName");
        String postId = formData.get("postId");
       List<LikeCheck> likeCheck = lService.verifyDoubleLike(nickName,postId);
        if( likeCheck.size() !=0){
            System.out.println(nickName+"이"+postId+"에 좋아요 해제");

            lService.delLikeCheck(likeCheck.get(0));
            Board b = bService.getUserBoard(Long.valueOf(postId));
            b.offLikeCnt();
            bService.updateBoard(b);
        }else{
            System.out.println("좋아요 누른적 없음");
        }
    }
}
