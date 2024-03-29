package studybackend.refrigeratorcleaner.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import studybackend.refrigeratorcleaner.dto.DetailRecipeDto;
import studybackend.refrigeratorcleaner.dto.MyRecipeDto;
import studybackend.refrigeratorcleaner.dto.RecipeSaveRequestDto;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.error.ErrorCode;
import studybackend.refrigeratorcleaner.service.RecipeService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;


    //이 레시피 저장 클릭시
    @PostMapping(value = "/recipe/save")
    public @ResponseBody ResponseEntity saveRecipe(@RequestBody RecipeSaveRequestDto saveRequestDto){

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String socialId = principal.getUsername();

        Long recipeId;

        //레시피 저장 수행
        try{
            recipeId = recipeService.saveRecipe(saveRequestDto, socialId);
        }catch(CustomException ce){
            throw new CustomException(ErrorCode.NOT_EXIST_USER_SOCIALID);
        }catch (Exception e){
            throw new CustomException(ErrorCode.SAVE_RECIPE_FAIL);
        }

        return new ResponseEntity<Long>(recipeId, HttpStatus.OK);
    }

    //저장한 레시피 간단목록
    @GetMapping("/recipe/myRecipe")
    public @ResponseBody ResponseEntity myRecipeList() {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String socialId = principal.getUsername();

        List<MyRecipeDto> myRecipeDtoList;

        //레시피 목록 List<MyRecipeDto> 형태로 가져오기
        try {
            myRecipeDtoList = recipeService.getRecipeList(socialId);
        }catch (Exception e){
            throw new CustomException(ErrorCode.FAILED_TO_GET_RECIPE_LIST);
        }

        return new ResponseEntity<List<MyRecipeDto>>(myRecipeDtoList, HttpStatus.OK);
    }

    //저장한 레시피 상세페이지
    @GetMapping("/recipe/myRecipe/{recipeId}")
    public @ResponseBody ResponseEntity detailRecipe(@PathVariable("recipeId") Long recipeId) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String socialId = principal.getUsername();

        //해당 유저가 조회할 수 있는 레시피인지.
        Boolean validated = null;

        try{
            validated = recipeService.validateRecipeUser(recipeId, socialId);
        }catch (CustomException ce){
            if(ce.getErrorCode() == ErrorCode.NOT_EXIST_USER_SOCIALID){
                throw new CustomException(ErrorCode.NOT_EXIST_USER_SOCIALID);
            }else{
                throw new CustomException(ErrorCode.NO_EXIST_RECIPEID);
            }
        }

        //로그인한 사용자가 해당 레시피를 저장한 사용자가 맞는지 검사
        if(!validated){
            throw new CustomException(ErrorCode.NO_PERMISSION_FOR_RECIPE);
        }

        DetailRecipeDto detailRecipeDto;

        //해당 레시피의 상세 정보를 DetailRecipeDto 형태로 가져온다.
        try{
            detailRecipeDto = recipeService.getDetailRecipe(recipeId);
        }catch (Exception e){
            throw new CustomException(ErrorCode.FAILED_TO_GET_DETAIL_RECIPE);
        }

        return new ResponseEntity<DetailRecipeDto>(detailRecipeDto, HttpStatus.OK);
    }
}
