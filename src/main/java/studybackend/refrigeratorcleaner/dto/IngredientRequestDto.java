package studybackend.refrigeratorcleaner.dto;

import java.util.List;

public class IngredientRequestDto { //추천받기 요청

    private List<String> ingredients;

    // 기본 생성자
    public IngredientRequestDto() {}

    // 세터 메서드
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    // 게터 메서드
    public List<String> getIngredients() {
        return ingredients;
    }
}
