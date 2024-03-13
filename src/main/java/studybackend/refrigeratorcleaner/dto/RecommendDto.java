package studybackend.refrigeratorcleaner.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

//RecommendService의 반환으로 쓰이는 dto
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendDto {

    private String foodName;
    private List<String> ingredients;
    private List<String> recipe;

    @Builder
    public RecommendDto(String foodName, List<String> ingredients, List<String> recipe) {
        this.foodName = foodName;
        this.ingredients = ingredients;
        this.recipe = recipe;
    }
}
