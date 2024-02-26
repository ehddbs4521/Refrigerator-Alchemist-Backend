package studybackend.refrigeratorcleaner.controller;

import lombok.extern.slf4j.Slf4j;
import studybackend.refrigeratorcleaner.dto.IngredientRequestDto;
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
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    //추천받기 클릭시
    @PostMapping(value = "/recipe/recommend")
    public @ResponseBody ResponseEntity recommend(@RequestBody IngredientRequestDto ingredients){

        RecommendDto recommendDto;
        log.info("dqdqdwqd");

        try{
            recommendDto = recommendService.recommend(ingredients.getIngredients());
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<RecommendDto>(recommendDto, HttpStatus.OK);
    }
}
