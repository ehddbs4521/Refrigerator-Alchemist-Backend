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

import java.util.ArrayList;

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
        } catch (Exception e){
            throw new CustomException(ErrorCode.FAILED_TO_MAKE_RECIPE);
        }

        return new ResponseEntity<RecommendDto>(recommendDto, HttpStatus.OK);
    }

    @GetMapping(value = "/recipe/recommend")
    public @ResponseBody ResponseEntity recommend(){

        RecommendDto recommendDto;;

        try{
            recommendDto = recommendService.recommend(makeApi().getIngredients());
        }catch (Exception e){
            throw new CustomException(ErrorCode.FAILED_TO_MAKE_RECIPE);
        }

        return new ResponseEntity<RecommendDto>(recommendDto, HttpStatus.OK);
    }

    public IngredientRequestDto makeApi(){
        IngredientRequestDto ingredientRequestDto = new IngredientRequestDto();
        ingredientRequestDto.setIngredients(new ArrayList<>());

        ingredientRequestDto.getIngredients().add("계란");
        ingredientRequestDto.getIngredients().add("파");
        ingredientRequestDto.getIngredients().add("김");
        return ingredientRequestDto;
    }
}
