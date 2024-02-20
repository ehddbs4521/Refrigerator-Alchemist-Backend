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
    private String imgUrl; //지피티가 생성한 임시 url
    private List<String> ingredients;
    private List<String> recipe;

    @Builder
    public RecommendDto(String foodName, String imgUrl, List<String> ingredients, List<String> recipe) {
        this.foodName = foodName;
        this.imgUrl = imgUrl;
        this.ingredients = ingredients;
        this.recipe = recipe;
    }
}
