package studybackend.refrigeratorcleaner.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studybackend.refrigeratorcleaner.dto.RecommendDto;
import studybackend.refrigeratorcleaner.entity.Recommend;
import studybackend.refrigeratorcleaner.repository.RecommendRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RecommendServiceTest {

    @Mock
    private RecommendRepository recommendRepository;

    @Mock
    private RecipeService recipeService;

    @Autowired
    private RecommendService recommendService;

    @InjectMocks
    private RecommendService mockRecommendService;


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
    @DisplayName("Recommend 잘 받아오는지")
    public void testGetRecommended() {

        Long recommendId = 1L;
        Recommend mockRecommend = Recommend.builder()
                .foodName("Test Food")
                .ingredientStr("Ingredient1, Ingredient2")
                .recipeStr("Step 1, Step 2")
                .build();
        when(recommendRepository.findByRecommendId(recommendId)).thenReturn(Optional.of(mockRecommend));
        doNothing().when(recommendRepository).delete(mockRecommend);
        RecommendDto result = mockRecommendService.getRecommended(recommendId);

        assertNotNull(result);
        assertEquals(mockRecommend.getFoodName(), result.getFoodName());

        verify(recommendRepository, times(1)).findByRecommendId(recommendId); // findByRecommendId 메서드가 한 번 호출되었는지 확인
        verify(recommendRepository, times(1)).delete(mockRecommend); // delete 메서드가 한 번 호출되었는지 확인
    }
}