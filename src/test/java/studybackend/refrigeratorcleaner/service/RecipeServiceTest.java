package studybackend.refrigeratorcleaner.service;

import studybackend.refrigeratorcleaner.dto.MyRecipeDto;
import studybackend.refrigeratorcleaner.dto.DetailRecipeDto;
import studybackend.refrigeratorcleaner.dto.RecommendDto;
import studybackend.refrigeratorcleaner.entity.Authority;
import studybackend.refrigeratorcleaner.entity.Member;
import studybackend.refrigeratorcleaner.entity.Recipe;
import studybackend.refrigeratorcleaner.repository.MemberRepository;
import studybackend.refrigeratorcleaner.repository.RecipeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class RecipeServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecommendService recommendService;

    public Member saveMember(){
        Member member = Member.builder()
                .email("test@test.com")
                .authority(Authority.ROLE_USER)
                .nickname("testNickName")
                .password("test1234")
                .profileUrl("testProfileURL")
                .providerName("KAKAO")
                .build();

        memberRepository.save(member);
        return member;
    }

    public Recipe makeRecipe(int n, Member member){
        //재료 리스트 만들기
        List<String> ingredientList = new ArrayList<>();
        ingredientList.add("음식" + n + " 재료1");
        ingredientList.add("음식" + n + " 재료2");
        ingredientList.add("음식" + n + " 재료3");
        ingredientList.add("음식" + n + " 재료4");

        //레시피 리스트 만들기
        List<String> recipeList = new ArrayList<>();
        recipeList.add("음식" + n + " 레시피1");
        recipeList.add("음식" + n + " 레시피2");
        recipeList.add("음식" + n + " 레시피3");
        recipeList.add("음식" + n + " 레시피4");

        DetailRecipeDto recipeDto = DetailRecipeDto.builder()
                .ingredients(ingredientList)
                .imgUrl("testImgURL"+n)
                .foodName("음식"+n)
                .recipe(recipeList)
                .build();

        Long recipeId = recipeService.saveRecipe(recipeDto, member.getEmail());
        return recipeRepository.findByRecipeId(recipeId);
    }

    @Test
    @DisplayName("레시피 추천, 저장, 조회 테스트")
    public void saveRecipe(){
        Member member = saveMember();

        //재료 리스트 만들기
        List<String> ingredientList = new ArrayList<>();
        ingredientList.add("양배추");
        ingredientList.add("감자");
        ingredientList.add("양파");
        ingredientList.add("두부");
        ingredientList.add("버섯");
        ingredientList.add("마늘");

        //레시피 추천받아 recommendDto에 받아옴
        RecommendDto recommendDto = recommendService.recommend(ingredientList);

        DetailRecipeDto recipeDto = DetailRecipeDto.builder()
                .recipe(recommendDto.getRecipe())
                .ingredients(recommendDto.getIngredients())
                .foodName(recommendDto.getFoodName())
                .imgUrl(recommendDto.getImgUrl())
                .build();

        //레시피 저장
        Long recipeId = recipeService.saveRecipe(recipeDto, member.getEmail());
        Recipe savedRecipe = recipeRepository.findByRecipeId(recipeId);

        assertEquals(recipeDto.getIngredients(), savedRecipe.getIngredientList());
        assertEquals(recipeDto.getRecipe(), savedRecipe.getRecipeList());
        assertEquals(member, savedRecipe.getMember());

        //myRecipe 목록 조회
        List<MyRecipeDto> myRecipeDtoList = recipeService.getRecipeList(member.getEmail());
        for (MyRecipeDto mr : myRecipeDtoList) {
            System.out.println(mr.getFoodName());
            System.out.println(mr.getIngredients());
            System.out.println();
        }

        MyRecipeDto first = myRecipeDtoList.get(0);
        //레시피 상세 조회
        DetailRecipeDto detailRecipeDto = recipeService.getDetailRecipe(first.getRecipeId());
        assertEquals(detailRecipeDto.getIngredients(), first.getIngredients());
        assertEquals(detailRecipeDto.getFoodName(), first.getFoodName());
    }
}