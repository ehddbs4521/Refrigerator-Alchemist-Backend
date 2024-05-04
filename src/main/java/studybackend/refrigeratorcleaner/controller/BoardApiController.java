package studybackend.refrigeratorcleaner.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studybackend.refrigeratorcleaner.dto.BoardDto;
import studybackend.refrigeratorcleaner.entity.Board;
import studybackend.refrigeratorcleaner.entity.LikeCheck;
import studybackend.refrigeratorcleaner.entity.User;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.error.ErrorCode;
import studybackend.refrigeratorcleaner.service.BoardService;
import studybackend.refrigeratorcleaner.service.LikeCheckService;
import studybackend.refrigeratorcleaner.service.MyPageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static studybackend.refrigeratorcleaner.error.ErrorCode.FAILED_TO_GET_RECIPE_LIST;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardApiController {
    private  final BoardService boardService;
    private final LikeCheckService likeCheckService;

    private  final MyPageService myPageService;

    public Map<String, Object> makeApi(List<BoardDto> boardDtos) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        for (BoardDto boardDto : boardDtos) {
            Map<String, Object> item = new HashMap<>();
            item.put("ID", String.valueOf(boardDto.getId()));
            item.put("description", boardDto.getTexts());
            item.put("nickName", boardDto.getNickName());
            item.put("title", boardDto.getTitle());
            item.put("email", boardDto.getEmail());
            item.put("likeCount",String.valueOf(boardDto.getLikeCount()));
            item.put("imageUrl",boardDto.getImageUrl());

            List<String> tmp  = new ArrayList<>(); //재료들이 들어갈 리스트
            if(boardDto.getIngredients() != null){  //재료 필드에 값이 있을때
                for(String ing : boardDto.getIngredients()){
                    tmp.add(ing);
                }
            }

            item.put("ingredients",tmp);
            items.add(item);
        }

        result.put("items", items);
        return result;
    }
    @GetMapping(value = "/board/page")

    public  Map<String, Object>
    getContent(@RequestParam(value = "data") String data){

        System.out.println("정답: "+ data);
        Map<String, Object> content;
        try {
            List<BoardDto> boardDtos = boardService.getBoard((Integer.valueOf(data)-1)*6);
            content = makeApi(boardDtos);
        }catch(Exception e) {
            throw new CustomException(FAILED_TO_GET_RECIPE_LIST);
        }
        return  content;
    }
    //상세페이지
    @GetMapping(value  = "board/specific")
    public  Map<String, Object> getSpecific(@RequestParam(value = "postId") String postId){
        log.info("postId:{}", postId);
        Long id = Long.valueOf(postId);
        Map<String, Object> content;
        try {
            List<BoardDto> boardDtos = boardService.getSpecific(id);
            content = makeApi(boardDtos);
        }catch(Exception e) {
            throw new CustomException(ErrorCode. FAILED_TO_GET_DETAIL_RECIPE);
        }
        return  content;
    }

    @GetMapping(value = "/ranking/top3") // 좋아요 순으로 정렬
    public  Map<String, Object> orderLikeCount() {
        Map<String, Object> content;
        try{
            List<BoardDto> boardDtos = boardService.orderLikeCount();
            content = makeApi(boardDtos);
        }catch(Exception e) {
            throw new CustomException(FAILED_TO_GET_RECIPE_LIST);
        }
        return  content;
    }
    //제목 검색기능
    @GetMapping(value = "board/searchTitle")
    public Map<String,Object> searchTitle(@RequestParam(value = "title") String title) {
        System.out.println("제목: "+ title);
        //검색하고 싶은 제목을 인자에 넣음
        Map<String, Object> content;
        try{
            List<BoardDto> boardDtos =  boardService.searchBoard(title);
            for(BoardDto boardDto : boardDtos){
                System.out.println("검색한 결과 제목: "+ boardDto.getTitle());
            }
            content =  makeApi(boardDtos);
        }catch(Exception e) {
            throw new CustomException(FAILED_TO_GET_RECIPE_LIST);
        }
        return content;
    }

    @GetMapping (value = "/likedpost")
    public Map<String,Object> myPageLike (@RequestHeader(value = "email") String email){

        Map<String,Object> content;
        List<LikeCheck> LikeChecks = likeCheckService.getMyLikeTitle(email);
        log.info("nickName:{}", email);
        List<BoardDto> boardDtos = new ArrayList<>();
        for(LikeCheck likeCheck:LikeChecks){
            Long boardId =Long.valueOf(likeCheck.getBoardId());
            System.out.println("내가 누른 좋아요: "+ boardId);
            try{
                boardDtos.add(boardService.getSpecific(boardId).get(0));
            }catch (Exception e) {
                System.out.println("내가 누른 좋아요 에러 "+boardId);
            }

        }
        content = makeApi(boardDtos);
        return content;
    }

    //email(고유값)으로 내간 쓴 게시물 목록 조회(마이페이지)
    @GetMapping(value = "mypost")
    public  Map<String, Object>
    myPage(@RequestHeader(value = "email") String email){

        Map<String, Object> content;
        try {
            List<BoardDto> boardDtos = boardService.myList(email);
            System.out.println("내가 작성한 게시물: "+email +"ㅅㅏㅇㅣㅈㅈㅡ: "+ boardDtos.size());
            content = makeApi(boardDtos);

        }catch(Exception e) {
            throw new CustomException(FAILED_TO_GET_RECIPE_LIST);
        }
        //HttpStatus.OK
        return  content;
    }
    @GetMapping(value = "/userinfo")
    public  Map<String,Object>getUserImage(@RequestHeader(value = "email") String email){
        log.info("fqfqffwf");
        System.out.println("프로필 이미지 "+ email);
        Map<String, Object> content = new HashMap<>();
        List<User> user =  myPageService.getUser(email);
        String image = user.get(0).getImageUrl();
        content.put("imageUrl",image);
        return  content;
    }
    //게시물 삭제 로직
    @PostMapping (value ="mypost/delete")
    public void deleteBoard(@RequestBody String id){
        Long ID = Long.valueOf(id);
        boardService.deleteBoard(ID);
    }



    @GetMapping (value ="board/updateBoard")
    public Map<String, Object> updateBoard(@RequestParam(value = "postId") String data){
        System.out.println("에러확인: "+data);

        Long ID = Long.valueOf(data);

        Map<String,Object> content;
        List<BoardDto> boardDtos = boardService.getSpecific(ID);
        System.out.println("업데이트 test");
        for (BoardDto boardDto : boardDtos) {
            System.out.println(boardDto.getNickName().getClass().getName());
            System.out.println(boardDto.getIngredients().getClass().getName());
            System.out.println(boardDto.getTitle().getClass().getName());
            System.out.println(boardDto.getTexts().getClass().getName());
        }
        try{
            content = makeApi(boardDtos);
        }catch(Exception e) {
            throw new CustomException(FAILED_TO_GET_RECIPE_LIST);
        }
        return content;
    }

    @GetMapping (value = "/mypost/size")
    public String mypostSize(@RequestHeader(value = "email") String email){
        System.out.println("mypost사이즈 "+ email);
        return String.valueOf(boardService.myList(email).size());
    }
    @GetMapping (value = "/likedpost/size")
    public  String likepostSize(@RequestHeader(value = "email") String email){
        return String.valueOf(likeCheckService.getMyLikeTitle(email));
    }
    //게시판 목록 전체 개수
    @GetMapping (value = "/board/total")
    public String boardSize(){
        return boardService.boardSize();
    }

    @GetMapping (value = "/apiDrill")
    public @ResponseBody ResponseEntity apiDrill(){

        BoardDto boardDto = boardService.getBoardDto(Long.valueOf(17));
        return new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK);
    }
}
