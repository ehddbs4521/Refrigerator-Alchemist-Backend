package studybackend.refrigeratorcleaner.controller;

import studybackend.refrigeratorcleaner.dto.RecommendDto;
import studybackend.refrigeratorcleaner.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    //추천받기 클릭시
    @PostMapping(value = "/recipe/recommend")
    public @ResponseBody ResponseEntity recommend(@RequestBody List<String> ingredients){

        RecommendDto recommendDto;

        try{
            recommendDto = recommendService.recommend(ingredients);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE); //406
        }

        return new ResponseEntity<RecommendDto>(recommendDto, HttpStatus.OK);
    }
}
