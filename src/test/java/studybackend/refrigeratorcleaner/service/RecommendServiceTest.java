package studybackend.refrigeratorcleaner.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Transactional
class RecommendServiceTest {

    @Autowired
    RecommendService recommendService;

    @Autowired
    RecipeService recipeService;

    @Test
    @DisplayName("추천받기 성공 테스트")
    public void recommendSuccessTest() throws Exception{
        List<String> ingredients = new ArrayList<>();
        ingredients.add("파");
        ingredients.add("김치");
        ingredients.add("양파");
        ingredients.add("마늘");
        ingredients.add("양배추");

        Long recommendId = recommendService.recommend(ingredients);

        assertNotNull(recommendId);
    }
}