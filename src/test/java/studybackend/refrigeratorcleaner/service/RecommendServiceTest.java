package studybackend.refrigeratorcleaner.service;

import studybackend.refrigeratorcleaner.dto.RecommendDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import studybackend.refrigeratorcleaner.entity.Recommend;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.repository.RecommendRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("getRecommended 테스트")
    public void getRecommendedTest() throws Exception{
        RecommendDto recommendDto = recommendService.getRecommended(recommendId);


        assertEquals(recommendDto.getFoodName(), "김치찌개");
        assertEquals(recommendDto.getIngredients(), recipeService.StringToList("김치/두부/돼지고기/양파/청양고추/대파"));
        assertEquals(recommendDto.getRecipe(), recipeService.StringToList("1. 먼저 돼지고기를 잘게 썰고, 김치와 양파도 잘게 썬다./2. 냄비에 식용유를 두르고 돼지고기를 볶다가 김치와 양파를 넣고 함께 볶는다./3. 물을 추가하고 끓여 두부와 미나리를 넣어 더 끓인다./4. 청양고추와 대파를 넣고 소금, 고추가루를 넣어 맛을 조절한다./5. 마지막으로 국간장을 조금 넣고 한소끔 더 끓인 후, 맛있게 드세요!"));

    }
}