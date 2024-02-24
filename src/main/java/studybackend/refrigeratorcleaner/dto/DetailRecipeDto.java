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
public class DetailRecipeDto {

    @NotBlank(message = "요리명은 필수 입력 값입니다.")
    private String foodName;

    private String imgUrl; //지피티가 생성한 임시 url

    @NotNull
    @Size(min = 1, message = "최소 1개 이상의 재료가 필요합니다.")
    private List<String> ingredients;

    @NotNull
    @Size(min = 1, message = "최소 한 줄 이상의 레시피가 필요합니다.")
    private List<String> recipe;

    @Builder
    public DetailRecipeDto(String foodName, String imgUrl, List<String> ingredients, List<String> recipe) {
        this.foodName = foodName;
        this.imgUrl = imgUrl;
        this.ingredients = ingredients;
        this.recipe = recipe;
    }


}
