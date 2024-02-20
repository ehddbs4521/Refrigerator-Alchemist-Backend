package studybackend.refrigeratorcleaner.controller;

import studybackend.refrigeratorcleaner.dto.RecipeDto;
import studybackend.refrigeratorcleaner.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;


    //이 레시피 저장 클릭시
    @PostMapping(value = "/recipe/save")
    public @ResponseBody ResponseEntity saveRecipe(@Valid RecipeDto recipeDto, BindingResult bindingResult, Principal principal){

        //recipeDto 검사
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long recipeId;
        try{
            recipeId = recipeService.saveRecipe(recipeDto, email);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(recipeId, HttpStatus.OK);
    }

    //저장한 레시피 보기 클릭
    @GetMapping("/recipe/MyRecipe")
    public @ResponseBody ResponseEntity recipeList(Principal principal) {
        String email = principal.getName();
        List<RecipeDto> recipeDtoList;

        try {
            recipeDtoList = recipeService.getRecipeList(email);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<List<RecipeDto>>(recipeDtoList, HttpStatus.OK);
    }
}
