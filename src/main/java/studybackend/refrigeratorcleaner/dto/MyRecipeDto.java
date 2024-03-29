package studybackend.refrigeratorcleaner.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyRecipeDto { //레시피 목록 응답

    private Long recipeId;

    private String foodName;

    private String ingredientStr;

    private List<String> ingredientList;

    @Builder
    public MyRecipeDto(Long recipeId, String foodName, String ingredientStr) {
        this.recipeId = recipeId;
        this.foodName = foodName;
        this.ingredientStr = ingredientStr;

        this.ingredientList = new ArrayList<>();
        for(String ingredient : ingredientStr.split("/")){
            ingredientList.add(ingredient.trim());
        }
    }
}
