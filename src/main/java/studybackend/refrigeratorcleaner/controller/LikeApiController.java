package studybackend.refrigeratorcleaner.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import studybackend.refrigeratorcleaner.dto.BoardDto;
import studybackend.refrigeratorcleaner.entity.Board;
import studybackend.refrigeratorcleaner.entity.LikeCheck;
import studybackend.refrigeratorcleaner.service.BoardService;
import studybackend.refrigeratorcleaner.service.LikeCheckService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LikeApiController {
    private final LikeCheckService lService;
    private final BoardService bService;

    @GetMapping("board/islike")
    public List<String> boardLike(@RequestParam(value = "id") String email){
        //i

        System.out.println("islike 들어옴" + email);

        List<String> answer = new ArrayList<>();
        List<LikeCheck> likeChecks = lService.getMyLikeTitle(email);
        for( LikeCheck likeCheck:likeChecks){
            answer.add(likeCheck.getBoardId());
        }
        return answer;
    }
    @PostMapping ("board/like")
    public void onClickLike(HttpServletRequest request,@RequestBody Map<String,String> formData){
        //,@RequestParam Map<String,String> formData
        String email= formData.get("email");
        String postId = formData.get("postId");
        if( lService.verifyDoubleLike(email,postId).size() ==0){
            System.out.println(email+"이"+postId+"에 좋아요 누름");
            LikeCheck likeCheck = LikeCheck.builder().
                    email(email).boardId(postId)
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
        String email= formData.get("email");
        String postId = formData.get("postId");
        List<LikeCheck> likeCheck = lService.verifyDoubleLike(email,postId);
        if( likeCheck.size() !=0){
            System.out.println(email+"이"+postId+"에 좋아요 해제");

            lService.delLikeCheck(likeCheck.get(0));
            Board b = bService.getUserBoard(Long.valueOf(postId));
            b.offLikeCnt();
            bService.updateBoard(b);
        }else{
            System.out.println("좋아요 누른적 없음");
        }
    }
}
