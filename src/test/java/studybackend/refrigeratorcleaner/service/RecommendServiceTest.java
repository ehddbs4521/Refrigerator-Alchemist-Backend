package studybackend.refrigeratorcleaner.service;

import studybackend.refrigeratorcleaner.dto.RecommendDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class RecommendServiceTest {

    @Autowired
    RecommendService recommendService;

    @Test
    @DisplayName("추천받기 테스트")
    public void recommendTest(){
        List<String> ingredients = new ArrayList<>();
        ingredients.add("파");
        ingredients.add("김치");
        ingredients.add("양파");
        ingredients.add("마늘");
        ingredients.add("양배추");

        RecommendDto recommendDto = recommendService.recommend(ingredients);

        System.out.println(recommendDto.getFoodName());
        System.out.println(recommendDto.getIngredients().toString());
        System.out.println(recommendDto.getRecipe().toString());
    }
}