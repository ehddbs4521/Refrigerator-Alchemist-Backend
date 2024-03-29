package studybackend.refrigeratorcleaner.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studybackend.refrigeratorcleaner.dto.IngredientRequestDto;
import studybackend.refrigeratorcleaner.dto.RecommendDto;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.error.ErrorCode;
import studybackend.refrigeratorcleaner.service.RecommendService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    //추천받기 클릭시
    @PostMapping(value = "/recipe/recommend")
    public @ResponseBody ResponseEntity recommend(@RequestBody IngredientRequestDto ingredients){

        //재료 입력했는지 검사
        if(ingredients.getIngredients().size() == 0){
            throw new CustomException(ErrorCode.NO_INGREDIENT);
        }

        Long recommendId;

        try{
            recommendId = recommendService.recommend(ingredients.getIngredients());
        }catch (Exception e){
            throw new CustomException(ErrorCode.FAILED_TO_MAKE_RECIPE);
        }

        return new ResponseEntity<Long>(recommendId, HttpStatus.OK);
    }

    @GetMapping(value = "/recipe/recommend/{recommendId}")
    public @ResponseBody ResponseEntity getRecommended(@PathVariable("recommendId") Long recommendId){

        RecommendDto recommendDto;

        try{
            recommendDto = recommendService.getRecommended(recommendId);
        }catch (CustomException ce){
            throw new CustomException(ErrorCode.NO_EXIST_RECOMMENDID);
        }

        return new ResponseEntity<RecommendDto>(recommendDto, HttpStatus.OK);
    }
}
