package studybackend.refrigeratorcleaner.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studybackend.refrigeratorcleaner.dto.DetailRecipeDto;
import studybackend.refrigeratorcleaner.dto.MyRecipeDto;
import studybackend.refrigeratorcleaner.dto.RecipeSaveRequestDto;
import studybackend.refrigeratorcleaner.entity.Recipe;
import studybackend.refrigeratorcleaner.entity.Recommend;
import studybackend.refrigeratorcleaner.entity.User;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.error.ErrorCode;
import studybackend.refrigeratorcleaner.repository.RecipeRepository;
import studybackend.refrigeratorcleaner.repository.RecommendRepository;
import studybackend.refrigeratorcleaner.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RecommendRepository recommendRepository;

    //List<String> -> 요소뒤에 /붙인 String으로 변환해서 반환
    public String listToString(List<String> strList){
        StringBuilder sb = new StringBuilder();

        for(String str : strList){
            sb.append(str+"/");
        }

        return sb.toString();
    }

    //요소뒤에 /붙인 String을 List<String>으로 변환해서 반환
    public List<String> StringToList(String str){
        List<String> newList = new ArrayList<>();

        for(String element : str.split("/")){
            newList.add(element.trim());
        }

        return newList;
    }

    //레시피 저장하기
    public Long saveRecipe(RecipeSaveRequestDto saveRequestDto, String socialId){
        User user = userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER_SOCIALID));

        //recipeDto의 List<String> 형식 recipe를 String으로 변환
        Recipe recipe = Recipe.builder()
                .user(user)
                .foodName(saveRequestDto.getFoodName())
                .ingredientStr(listToString(saveRequestDto.getIngredients()))
                .recipeStr(listToString(saveRequestDto.getRecipe()))
                .build();

        recipeRepository.save(recipe);

        return recipe.getRecipeId();
    }

    @Transactional(readOnly = true)
    //레시피 목록 조회하기
    public List<MyRecipeDto> getRecipeList(String socialId) {
        List<MyRecipeDto> myRecipeDtoList = recipeRepository.findRecipeDtoList(socialId);

        return myRecipeDtoList;
    }

    @Transactional(readOnly = true)
    //상세레시피 가져오기
    public DetailRecipeDto getDetailRecipe(Long recipeId){
        Recipe recipe = recipeRepository.findByRecipeId(recipeId).get();
        DetailRecipeDto detailRecipeDto = DetailRecipeDto.builder()
                .foodName(recipe.getFoodName())
                .ingredients(StringToList(recipe.getIngredientStr()))
                .recipe(StringToList(recipe.getRecipeStr()))
                .build();

        return detailRecipeDto;
    }

    //해당 유저가 조회할 수 있는 레시피인지.
    @Transactional(readOnly = true)
    public boolean validateRecipeUser(Long recipeId, String socialId){
        User user = userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER_SOCIALID));
        Recipe recipe = recipeRepository.findByRecipeId(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_EXIST_RECIPEID));

        User savedMember = recipe.getUser();

        if(!StringUtils.equals(user.getSocialId(), savedMember.getSocialId())){
            return false;
        }

        return true;
    }
}
