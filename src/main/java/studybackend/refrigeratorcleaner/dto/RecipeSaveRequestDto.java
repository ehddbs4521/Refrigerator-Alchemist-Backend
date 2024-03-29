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
public class RecipeSaveRequestDto { //레시피 저장 요청

    private String foodName;

    private List<String> ingredients;

    private List<String> recipe;

    @Builder
    public RecipeSaveRequestDto(String foodName, List<String> ingredients, List<String> recipe) {
        this.foodName = foodName;
        this.ingredients = ingredients;
        this.recipe = recipe;
    }

}
