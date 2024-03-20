package studybackend.refrigeratorcleaner.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import studybackend.refrigeratorcleaner.Entity.Board;
import studybackend.refrigeratorcleaner.Entity.User;
import studybackend.refrigeratorcleaner.Service.BoardService;
import studybackend.refrigeratorcleaner.Service.MyPageService;
import studybackend.refrigeratorcleaner.dto.BoardDto;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.error.ErrorCode;

import java.awt.print.Pageable;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class BoardApiController {
    private  final BoardService boardService;
    private  final MyPageService myPageService;

    public Map<String, Object> makeApi(List<Board> boards) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        for (Board board : boards) {
            Map<String, Object> item = new HashMap<>();
            item.put("ID", String.valueOf(board.getId()));
            item.put("Recipe", board.getTexts());
            item.put("nickName", board.getNickName());
            item.put("title", board.getTitle());
            item.put("email", board.getEmail());
            item.put("likeCount",String.valueOf(board.getLikeCount()));
            item.put("imageUrl",board.getImageUrl());

            List<String> tmp  = new ArrayList<>(); //재료들이 들어갈 리스트
            if(board.getIngredients() != null){  //재료 필드에 값이 있을때
                for(String ing : board.getIngredients()){
                    tmp.add(ing);
                }
            }

            item.put("ingredients",tmp);
            items.add(item);
        }

        result.put("items", items);
        return result;
    }
    @PostMapping(value = "board/apiTest")
    //@CrossOrigin(origins =  "board/apiTest") //최신순으로 출력
    public  Map<String, Object>
    getContent(@RequestBody String data){
        //
        //
        System.out.println("정답: "+ data);
        Map<String, Object> content;
        try {
            List<Board> boards = boardService.getBoard((Integer.valueOf(data)-1)*6);
            content = makeApi(boards);
        }catch(Exception e) {
            throw new CustomException(ErrorCode. FAILED_TO_GET_RECIPE_LIST);
        }
        return  content;
    }
    //상세페이지
    @PostMapping(value  = "board/specific")
    public  Map<String, Object> getSpecific(@RequestBody String formData){
        System.out.println("상세 : "+formData);
        Long id = Long.valueOf(formData);
        Map<String, Object> content;
        try {
            List<Board> boards = boardService.getSpecific(id);
            content = makeApi(boards);
        }catch(Exception e) {
            throw new CustomException(ErrorCode. FAILED_TO_GET_DETAIL_RECIPE);
        }
        return  content;
    }
    @GetMapping(value = "board/apiTestLikeCount") // 좋아요 순으로 정렬
    public  Map<String, Object> orderLikeCount() {
        Map<String, Object> content;
        try{
        List<Board> boards = boardService.orderLikeCount();
        content = makeApi(boards);
        }catch(Exception e) {
            throw new CustomException(ErrorCode. FAILED_TO_GET_RECIPE_LIST);
        }
        return  content;
    }
    //제목 검색기능
    @PostMapping(value = "board/searchTitle")
    public Map<String,Object> searchTitle() {
        //검색하고 싶은 제목을 인자에 넣음
        Map<String, Object> content;
        try{
            List<Board> boards =  boardService.searchBoard("qwe");
           content =  makeApi(boards);
        }catch(Exception e) {
            throw new CustomException(ErrorCode. FAILED_TO_GET_RECIPE_LIST);
        }
        return content;
    }



    //email(고유값)으로 내간 쓴 게시물 목록 조회(마이페이지)
    @PostMapping(value = "board/myPage")
    public  Map<String, Object>
    myPage(@RequestBody String nickName){

        Map<String, Object> content;
        try {
            List<Board> boards = boardService.myList(nickName);
            content = makeApi(boards);

        }catch(Exception e) {
            throw new CustomException(ErrorCode. FAILED_TO_GET_RECIPE_LIST);
        }
        return  content;
    }
    @PostMapping(value = "board/userprofile")
    public  Map<String,Object>getUserImage(@RequestBody String nickName){
        System.out.println("프로필 이미지 "+ nickName);
        Map<String, Object> content = new HashMap<>();
        List<User> user =  myPageService.getUser(nickName);
        String image = user.get(0).getImageUrl();
        content.put("imageUrl",image);
        return  content;
    }
    //게시물 삭제 로직
    @PostMapping (value ="board/deleteBoard")
    public void deleteBoard(@RequestBody String id){
        id = id.split(":")[1];
        id= id.substring(0,id.length()-1);

        Long ID = Long.valueOf(id);
        Board b =  boardService.getSpecific(ID).get(0);
        boardService.deleteBoard(b);
    }



    @PostMapping (value ="board/updateBoard")
    public Map<String, Object> updateBoard(@RequestBody String data){
        System.out.println("에러확인: "+data);
//        formData = formData.split(":")[1];
//        formData= formData.substring(1,formData.length()-2);
        Long ID = Long.valueOf(data);
        //System.out.println("파싱결과: "+formData.get("postId"));
        Map<String,Object> content;
        List<Board> boards = boardService.getSpecific(ID);
        System.out.println("업데이트 test");
        for (Board board : boards) {
            System.out.println(board.getNickName().getClass().getName());
            System.out.println(board.getIngredients().getClass().getName());
            System.out.println(board.getTitle().getClass().getName());
            System.out.println(board.getTexts().getClass().getName());
        }
        try{
            content = makeApi(boards);
        }catch(Exception e) {
            throw new CustomException(ErrorCode. FAILED_TO_GET_RECIPE_LIST);
        }
        return content;
    }
    //게시판 목록 전체 개수
    @GetMapping (value = "boardSize")
    public String boardSize(){
        return boardService.boardSize();
    }
}
