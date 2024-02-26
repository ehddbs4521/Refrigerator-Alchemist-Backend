package studybackend.refrigeratorcleaner.controller;

import org.springframework.web.bind.annotation.*;
import studybackend.refrigeratorcleaner.dto.MyRecipeDto;
import studybackend.refrigeratorcleaner.dto.DetailRecipeDto;
import studybackend.refrigeratorcleaner.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;


    //이 레시피 저장 클릭시
//    @PostMapping(value = "/recipe/save")
//    public @ResponseBody ResponseEntity saveRecipe(@Valid @RequestBody DetailRecipeDto recipeDto, BindingResult bindingResult, Principal principal){
//
//        //recipeDto 검사
//        if(bindingResult.hasErrors()){
//            StringBuilder sb = new StringBuilder();
//            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//
//            for (FieldError fieldError : fieldErrors) {
//                sb.append(fieldError.getDefaultMessage());
//            }
//
//            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
//        }
//
//        String email = principal.getName();
//        Long recipeId;
//        try{
//            recipeId = recipeService.saveRecipe(recipeDto, email);
//        }catch (Exception e){
//            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<Long>(recipeId, HttpStatus.OK);
//    }

    //저장한 레시피 간단목록
//    @GetMapping("/recipe/myRecipe")
//    public @ResponseBody ResponseEntity myRecipeList(Principal principal) {
//        String email = principal.getName();
//        List<MyRecipeDto> myRecipeDtoList;
//
//        try {
//            myRecipeDtoList = recipeService.getRecipeList(email);
//        }catch (Exception e){
//            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<List<MyRecipeDto>>(myRecipeDtoList, HttpStatus.OK);
//    }

    //저장한 레시피 상세페이지
//    @GetMapping("/recipe/myRecipe/{recipeId}")
//    public @ResponseBody ResponseEntity detailRecipe(@PathVariable("recipeId") Long recipeId) {
//        DetailRecipeDto detailRecipeDto;
//
//        try{
//            detailRecipeDto = recipeService.getDetailRecipe(recipeId);
//        }catch (Exception e){
//            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<DetailRecipeDto>(detailRecipeDto, HttpStatus.OK);
//    }
}
