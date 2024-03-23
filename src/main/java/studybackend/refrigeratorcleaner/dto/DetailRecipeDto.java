package studybackend.refrigeratorcleaner.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailRecipeDto { //상세레시피 조회 서버 응답

    private String foodName;

    private List<String> ingredients;

    private List<String> recipe;

    @Builder
    public DetailRecipeDto(String foodName, List<String> ingredients, List<String> recipe) {
        this.foodName = foodName;
        this.ingredients = ingredients;
        this.recipe = recipe;
    }
}
