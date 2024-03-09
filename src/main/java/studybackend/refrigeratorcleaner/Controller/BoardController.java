package studybackend.refrigeratorcleaner.Controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import studybackend.refrigeratorcleaner.Entity.Board;
import studybackend.refrigeratorcleaner.Entity.BoardContent;
import studybackend.refrigeratorcleaner.Entity.LikeCheck;
import studybackend.refrigeratorcleaner.Form.LoginForm;
import studybackend.refrigeratorcleaner.Service.BoardService;

import studybackend.refrigeratorcleaner.Service.ImageService;
import studybackend.refrigeratorcleaner.Service.LikeCheckService;
import studybackend.refrigeratorcleaner.dto.BoardDto;
import studybackend.refrigeratorcleaner.dto.LikeDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {
    private final BoardService bService ;
    private final LikeCheckService lService;
    private  final ImageService iService;

    @GetMapping(value = "/board")
    public  String Board(HttpServletRequest request) {
        return "home";
    }
    @PostMapping(value =  "/board")
    public String  postMainPage(@Valid LoginForm form , BindingResult result, HttpServletRequest request, HttpServletResponse response)
    {
        return "redirect:/board";
    }
//    @GetMapping(value ="/write")
//    public String writeContent(HttpServletRequest request) {return "writeContent";}

    @PostMapping(value = "/content/write")//작성한 게시글을 저장
    public String postWriteContent(@RequestBody BoardDto board) {
        //System.out.println("good");
//        Board newBoard = Board.builder()
//                .email("test@naver.com")
//                .nickName("test")
//                .likeCount(0)
//                .title(board.getTitle())  // 기존 board의 title을 가져옴
//                .build();
//        board.setEmail("delTest@naver.com");
//        boardContent.setEmail("delTest@naver.com");
//        board.setLikeCount(0);
//        board.setNickName("delTest");
//        boardContent.setNickName("delTest");
//        bService.saveBoard(board);
//        bService.saveBoardContent(boardContent);
        System.out.println("성공");
        System.out.println(board.getTexts());
        Board boarEntity =  bService.getBoarEntity(board);
        bService.saveBoard(boarEntity);
        return "redirect:/board";
    }

    @PostMapping("/writeTest")
    public ResponseEntity<String> writeContent(HttpServletRequest request,@RequestParam Map<String, String> formData) throws IOException {
        MultipartFile image = ((MultipartHttpServletRequest) request).getFile("image");
        InputStream imageInputStream = null;
        String imageUrl  = "";
        try { //InputStream으로 변환해주는 코드
            imageInputStream = image.getInputStream();

            imageUrl= iService.uploadImage(imageInputStream,"test");
        } catch (IOException e) {
            e.printStackTrace();
            // 이미지 파일을 InputStream으로 변환하는 중에 예외 발생 시 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error converting image to InputStream");
        }


        String foodName = request.getParameter("foodName");
        String description = request.getParameter("description");
        //String[] ingredients = request.getParameterValues("ingredient");
        List<String> ingredients = new ArrayList<>();
        formData.forEach((key, value) -> {
            if (key.startsWith("ingredient")) {
                ingredients.add(value);
            }
        });
        // 파일 및 데이터 처리 로직을 수행합니다.
        // 예를 들어, 해당 데이터를 데이터베이스에 저장하거나 다른 처리를 수행할 수 있습니다.

        try {
            System.out.println("성공");
            System.out.println(foodName);
            System.out.println(description);
            for(String ing :ingredients ){
                System.out.println(ing);
            }

            Board b = Board.builder().
                    email("test@naver.com").
                    nickName("test").
                    title(foodName).
                    texts(description).
                    imageUrl(imageUrl).
                    build();
            bService.saveBoard(b);
            //contentService.saveContent(image, foodName, description, ingredients);
            return ResponseEntity.ok("Content uploaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading content");
        }
    }
    @GetMapping (value = "board/likeApi") // 좋아요 누르기
    public String clickLike(@RequestBody LikeDto likeDto) {

        String nickName = "test";
        String title = "jxckmnjv";
        String clickerName = "changeme";
        //좋아요 누르기 전 '좋아요' 중복클릭 했는지 체크
        if(lService.verifyDoubleLike(nickName,title,clickerName).size()==0){
            //좋아요 클릭할 게시물의 제목으로 검색해 가져옴
            List<Board> boards =  bService.searchBoard(title);
            //boards.get(0).setLikeCount(boards.get(0).getLikeCount()+1); //좋아요 1증가
            boards.get(0).builder().likeCount(boards.get(0).getLikeCount()+1).build();//좋아요 1증가
            bService.updateBoard(boards.get(0)); //변경된 좋아요 수를 db에 반영


            LikeCheck l = lService.getLikeCheck(likeDto);
            //LikeCheck l = new LikeCheck(lDto.getNickName(),lDto.getTitle(),lDto.getClickerName());
            lService.logLikeCheck(l);
        }
        return "home";
    }
}
