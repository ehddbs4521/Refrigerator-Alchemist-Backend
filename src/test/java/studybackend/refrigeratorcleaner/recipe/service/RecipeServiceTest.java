package studybackend.refrigeratorcleaner.recipe.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import studybackend.refrigeratorcleaner.dto.DetailRecipeDto;
import studybackend.refrigeratorcleaner.dto.MyRecipeDto;
import studybackend.refrigeratorcleaner.dto.RecipeSaveRequestDto;
import studybackend.refrigeratorcleaner.entity.Recipe;
import studybackend.refrigeratorcleaner.entity.User;
import studybackend.refrigeratorcleaner.repository.RecipeRepository;
import studybackend.refrigeratorcleaner.repository.UserRepository;
import studybackend.refrigeratorcleaner.service.RecipeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    @DisplayName("레시피 저장 테스트")
    public void testSaveRecipe() {

        RecipeSaveRequestDto saveRequestDto = RecipeSaveRequestDto.builder()
                .foodName("TestFoodName")
                .ingredients(List.of("Ingredient1", "Ingredient2"))
                .recipe(List.of("Step 1", "Step 2")).build();

        User mockUser = User.builder().socialId("testSocialId").build();

        when(userRepository.findBySocialId("testSocialId")).thenReturn(Optional.of(mockUser));

        Long recipeId = recipeService.saveRecipe(saveRequestDto, "testSocialId");

        verify(userRepository, times(1)).findBySocialId("testSocialId");
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }


    @Test
    @DisplayName("레시피 목록 조회 테스트")
    public void testGetRecipeList() {
        String socialId = "testSocialId";

        List<MyRecipeDto> testRecipeList = new ArrayList<>();
        testRecipeList.add(new MyRecipeDto(1L, "testFoodName1", "testingre11/testingre12"));
        testRecipeList.add(new MyRecipeDto(2L, "testFoodName2", "testingre21/testingre22"));

        when(recipeRepository.findRecipeDtoList(socialId)).thenReturn(testRecipeList);
        List<MyRecipeDto> returnedRecipeList = recipeService.getRecipeList(socialId);

        assertNotNull(returnedRecipeList);
        assertEquals(testRecipeList.size(), returnedRecipeList.size());
        assertEquals(testRecipeList.get(0).getRecipeId(), returnedRecipeList.get(0).getRecipeId());
        assertEquals(testRecipeList.get(0).getFoodName(), returnedRecipeList.get(0).getFoodName());
        assertEquals(testRecipeList.get(0).getIngredientStr(), returnedRecipeList.get(0).getIngredientStr());
        assertEquals(testRecipeList.get(1).getRecipeId(), returnedRecipeList.get(1).getRecipeId());
        assertEquals(testRecipeList.get(1).getFoodName(), returnedRecipeList.get(1).getFoodName());
        assertEquals(testRecipeList.get(1).getIngredientStr(), returnedRecipeList.get(1).getIngredientStr());
    }

    @Test
    @Transactional(readOnly = true)
    @DisplayName("상세 레시피 조회 테스트")
    public void testGetDetailRecipe() {

        Long recipeId = 1L;
        Recipe mockRecipe = Recipe.builder()
                .foodName("Test Food")
                .ingredientStr("Ingredient1/Ingredient2")
                .recipeStr("1.recipe1/2.recipe2").build();

        when(recipeRepository.findByRecipeId(recipeId)).thenReturn(Optional.of(mockRecipe));

        DetailRecipeDto detailRecipeDto = recipeService.getDetailRecipe(recipeId);

        assertNotNull(detailRecipeDto);
        assertEquals("Test Food", detailRecipeDto.getFoodName());
        assertEquals(2, detailRecipeDto.getIngredients().size());
        assertEquals(2, detailRecipeDto.getRecipe().size());
    }

    @Test
    @Transactional(readOnly = true)
    @DisplayName("레시피&유저 유효성 테스트")
    public void testValidateRecipeUser() {

        Long recipeId = 1L;
        String socialId = "testSocialId";

        User mockUser = User.builder()
                .socialId(socialId).build();

        Recipe mockRecipe = Recipe.builder()
                .recipeId(recipeId)
                .user(mockUser).build();

        when(userRepository.findBySocialId(socialId)).thenReturn(Optional.of(mockUser));
        when(recipeRepository.findByRecipeId(recipeId)).thenReturn(Optional.of(mockRecipe));

        boolean isValidUser = recipeService.validateRecipeUser(recipeId, socialId);

        assertTrue(isValidUser);
    }
}