package studybackend.refrigeratorcleaner.recommend.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import studybackend.refrigeratorcleaner.dto.RecommendDto;
import studybackend.refrigeratorcleaner.entity.Recommend;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.error.ErrorCode;
import studybackend.refrigeratorcleaner.repository.RecommendRepository;
import studybackend.refrigeratorcleaner.service.RecipeService;
import studybackend.refrigeratorcleaner.service.RecommendService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendServiceTest {

    @Mock
    private RecommendRepository recommendRepository;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecommendService recommendService;

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
        RecommendDto result = recommendService.getRecommended(recommendId);

        assertNotNull(result);
        assertEquals(mockRecommend.getFoodName(), result.getFoodName());

        verify(recommendRepository, times(1)).findByRecommendId(recommendId); // findByRecommendId 메서드가 한 번 호출되었는지 확인
        verify(recommendRepository, times(1)).delete(mockRecommend); // delete 메서드가 한 번 호출되었는지 확인
    }

    @Test
    @DisplayName("존재하지 않는 recommendId")
    public void noRecommendIdTest(){

        Long recommendId = 123L;
        when(recommendRepository.findByRecommendId(recommendId)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            recommendService.getRecommended(recommendId);
        });

        assertEquals(ErrorCode.NO_EXIST_RECOMMENDID, exception.getErrorCode());
    }
}