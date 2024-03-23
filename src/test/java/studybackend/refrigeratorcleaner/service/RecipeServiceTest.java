package studybackend.refrigeratorcleaner.service;

import jakarta.persistence.EntityExistsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import studybackend.refrigeratorcleaner.dto.DetailRecipeDto;
import studybackend.refrigeratorcleaner.dto.MyRecipeDto;
import studybackend.refrigeratorcleaner.dto.RecipeSaveRequestDto;
import studybackend.refrigeratorcleaner.entity.Recipe;
import studybackend.refrigeratorcleaner.entity.Recommend;
import studybackend.refrigeratorcleaner.entity.User;
import studybackend.refrigeratorcleaner.repository.RecipeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import studybackend.refrigeratorcleaner.repository.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class RecipeServiceTest {

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecommendService recommendService;

    @Autowired
    UserRepository userRepository;

    //User 생성
    public User createUser() {

        User newUser = User.builder()
                .id(Integer.toUnsignedLong(1))
                .email("test@test.com")
                .imageUrl("https://naengtul-profile.s3.ap-northeast-2.amazonaws.com/favicon.png")
                .role("user")
                .refreshToken("fdsfadfsadas")
                .password("testpassword")
                .socialId("MySocialId")
                .nickName("test")
                .socialType("kakao")
                .build();

        return userRepository.save(newUser);
    }

    public RecipeSaveRequestDto createRecipeSaveDto(){
        List<String> ingredientList = new ArrayList<>();
        ingredientList.add( "김치");
        ingredientList.add( "두부");
        ingredientList.add( "돼지고기");
        ingredientList.add( "양파");
        ingredientList.add( "청양고추");
        ingredientList.add( "대파");

        List<String> recipeList = new ArrayList<>();
        recipeList.add("1. 먼저 돼지고기를 잘게 썰고, 김치와 양파도 잘게 썬다.");
        recipeList.add("2. 냄비에 식용유를 두르고 돼지고기를 볶다가 김치와 양파를 넣고 함께 볶는다.");
        recipeList.add("3. 물을 추가하고 끓여 두부와 미나리를 넣어 더 끓인다.");
        recipeList.add("4. 청양고추와 대파를 넣고 소금, 고추가루를 넣어 맛을 조절한다.");
        recipeList.add("5. 마지막으로 국간장을 조금 넣고 한소끔 더 끓인 후, 맛있게 드세요!");

        RecipeSaveRequestDto saveRequestDto = RecipeSaveRequestDto.builder()
                .recipe(recipeList)
                .ingredients(ingredientList)
                .foodName("김치찌개")
                .build();

        return saveRequestDto;
    }

    //입력받은 유저로 입력받은 레시피 저장
    public void saveRecipe(User user, int n){
        Recipe recipe = Recipe.builder()
                .user(user)
                .foodName("foodName"+n)
                .recipeStr("testRecipeStr"+n)
                .ingredientStr("ingredientStr"+n)
                .build();

        recipeRepository.save(recipe);
    }

    //입력받은 유저로 입력받은 레시피 저장&레시피ID 반환
    public Long saveAndGetRecipe(User user){
        Recipe recipe = Recipe.builder()
                .user(user)
                .foodName("foodName")
                .recipeStr("testRecipeStr")
                .ingredientStr("ingredientStr")
                .build();

        recipeRepository.save(recipe);

        return recipe.getRecipeId();
    }

    //List<String> -> 요소뒤에 /붙인 String으로 변환해서 반환
    public String listToString(List<String> strList){
        StringBuilder sb = new StringBuilder();

        for(String str : strList){
            sb.append(str+"/");
        }

        return sb.toString();
    }

    @Test
    @DisplayName("레시피 저장 테스트")
    @WithMockUser(roles = "USER")
    void saveRecipTest(){
        //멤버 생성
        User user = createUser();
        //DetailRecipeDto 생성
        RecipeSaveRequestDto saveRequestDto = createRecipeSaveDto();

        String socialId = user.getSocialId();

        Long recipeId = recipeService.saveRecipe(saveRequestDto, socialId);
        Recipe savedRecipe = recipeRepository.findByRecipeId(recipeId)
                .orElseThrow(EntityExistsException::new);

        String strRecipe = listToString(saveRequestDto.getRecipe());
        String strIngredient = listToString(saveRequestDto.getIngredients());

        assertEquals(saveRequestDto.getFoodName(), savedRecipe.getFoodName());
        assertEquals(strRecipe, savedRecipe.getRecipeStr());
        assertEquals(strIngredient, savedRecipe.getIngredientStr());
        System.out.println(savedRecipe.getRecipeStr());
        System.out.println(savedRecipe.getIngredientStr());
    }

    @Test
    @DisplayName("레시피 목록 조회 테스트")
    @WithMockUser(roles = "USER")
    void getRecipeListTest(){
        //멤버 생성
        User user = createUser();

        //레시피 생성 및 저장
        saveRecipe(user, 1);
        saveRecipe(user, 2);
        saveRecipe(user, 3);
        saveRecipe(user, 4);

        //레시피 목록 조회
        List<MyRecipeDto> myRecipeDtoList = recipeService.getRecipeList(user.getSocialId());

        assertEquals(myRecipeDtoList.size(), 4);
        for(int i = 0; i<4; i++){
            assertEquals(myRecipeDtoList.get(i).getFoodName(), "foodName"+(4-i));
            assertEquals(myRecipeDtoList.get(i).getIngredientStr(), "ingredientStr"+(4-i));
        }
    }

    @Test
    @DisplayName("상세 레시피 조회 테스트")
    @WithMockUser(roles = "USER")
    void getDetailRecipeTest(){
        //멤버 생성
        User user = createUser();

        //레시피 생성 및 저장
        Long recipeId1 = saveAndGetRecipe(user);

        //상세 레시피 조회
        DetailRecipeDto detailRecipeDto = recipeService.getDetailRecipe(recipeId1);

        assertEquals(detailRecipeDto.getFoodName(), "foodName");
        assertEquals(detailRecipeDto.getRecipe(), recipeService.StringToList("testRecipeStr"));
        assertEquals(detailRecipeDto.getIngredients(), recipeService.StringToList("ingredientStr"));
    }
}