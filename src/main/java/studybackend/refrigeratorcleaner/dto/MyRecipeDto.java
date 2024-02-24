package studybackend.refrigeratorcleaner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyRecipeDto {

    private Long recipeId;

    private String foodName;

    private List<String> ingredients;

    @Builder
    public MyRecipeDto(Long recipeId, String foodName, List<String> ingredients) {
        this.recipeId = recipeId;
        this.foodName = foodName;
        this.ingredients = ingredients;
    }
}
