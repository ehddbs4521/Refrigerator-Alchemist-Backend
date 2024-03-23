package studybackend.refrigeratorcleaner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import studybackend.refrigeratorcleaner.dto.DetailRecipeDto;
import studybackend.refrigeratorcleaner.dto.MyRecipeDto;
import studybackend.refrigeratorcleaner.dto.RecipeSaveRequestDto;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.error.ErrorCode;
import studybackend.refrigeratorcleaner.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;


    //이 레시피 저장 클릭시
    @PostMapping(value = "/recipe/save")
    public @ResponseBody ResponseEntity saveRecipe(@RequestBody RecipeSaveRequestDto saveRequestDto){

        log.info("bbbbbbbb");

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails principal = (UserDetails) authentication.getPrincipal();
//        log.info("socialId:{}",principal.getUsername());

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String socialId = principal.getUsername();
        log.info("socialId:{}",principal.getUsername());
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        log.info("socialId:{}",principal.getUsername());

        String socialId = principal.getUsername();

        //로그인한 사용자인지 검사
        if (socialId == "anonymousUser"){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        log.info("socialId:{}",principal.getUsername());

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
        }catch (CustomException ce){
            throw new CustomException(ErrorCode.NO_EXIST_RECIPEID);
        }
        catch (Exception e){
            throw new CustomException(ErrorCode.FAILED_TO_GET_DETAIL_RECIPE);
        }

        return new ResponseEntity<DetailRecipeDto>(detailRecipeDto, HttpStatus.OK);
    }

    /////////////////////////////////////////IP 테스트 전용 컨트롤러 ////////////////////////////////////////////////

    @GetMapping(value = "/recipe/save")
    public @ResponseBody ResponseEntity saveRecipe(){

        List<String> ingredientList = new ArrayList<>();
        ingredientList.add( "김치");
        ingredientList.add( "두부");
        ingredientList.add( "돼지고기");
        ingredientList.add( "양파");
        ingredientList.add( "청양고추");
        ingredientList.add( "대파");

        List<String> recipeList = new ArrayList<>();
        recipeList.add("1. 먼저 돼지고기를 잘게 썰고, 김치와 양파도 잘게 썬다.");
        recipeList.add("2. 냄비에 식용유를 두르고 돼지고기를 볶다가 김치와 양파를 넣고 함께 볶는다.");
        recipeList.add("3. 물을 추가하고 끓여 두부와 미나리를 넣어 더 끓인다.");
        recipeList.add("4. 청양고추와 대파를 넣고 소금, 고추가루를 넣어 맛을 조절한다.");
        recipeList.add("5. 마지막으로 국간장을 조금 넣고 한소끔 더 끓인 후, 맛있게 드세요!");

        RecipeSaveRequestDto saveRequestDto = RecipeSaveRequestDto.builder()
                .recipe(recipeList)
                .ingredients(ingredientList)
                .foodName("김치찌개")
                .build();

        String socialId = "MySocialId" ;// 워크벤치에서 테스트 유저 만들기
        Long recipeId;
        try{
            recipeId = recipeService.saveRecipe(saveRequestDto, socialId);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(recipeId, HttpStatus.OK);
    }

    @GetMapping(value = "/MyRecipe")
    public @ResponseBody ResponseEntity myRecipe() {
        List<MyRecipeDto> myRecipeDtoList;

        //레시피 목록 List<MyRecipeDto> 형태로 가져오기
        try {
            myRecipeDtoList = recipeService.getRecipeList("MySocialId");
        }catch (Exception e){
            throw new CustomException(ErrorCode.FAILED_TO_GET_RECIPE_LIST);
        }

        return new ResponseEntity<List<MyRecipeDto>>(myRecipeDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/myRecipe/{recipeId}")
    public @ResponseBody ResponseEntity detailRecipePage(@PathVariable("recipeId") Long recipeId) {

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
