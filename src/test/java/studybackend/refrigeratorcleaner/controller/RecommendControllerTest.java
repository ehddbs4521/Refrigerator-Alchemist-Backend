package studybackend.refrigeratorcleaner.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import studybackend.refrigeratorcleaner.dto.IngredientRequestDto;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.error.ErrorCode;
import studybackend.refrigeratorcleaner.service.RecommendService;
import org.junit.jupiter.api.BeforeEach;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class RecommendControllerTest {

    @Mock
    private RecommendService recommendService;

    @InjectMocks
    private RecommendController recommendController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("빈 재료 리스트가 들어온다면 에러 발생")
    public void testRecommend_NoIngredients_ThrowsCustomException() {

        IngredientRequestDto ingredients = new IngredientRequestDto();
        ingredients.setIngredients(Collections.emptyList());

        CustomException exception = assertThrows(CustomException.class, () -> {
            recommendController.recommend(ingredients);
        });

        assertEquals(ErrorCode.NO_INGREDIENT, exception.getErrorCode());
    }

}