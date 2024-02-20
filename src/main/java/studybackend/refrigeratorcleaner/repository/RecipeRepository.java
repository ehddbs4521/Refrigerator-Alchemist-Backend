package studybackend.refrigeratorcleaner.repository;

import studybackend.refrigeratorcleaner.dto.RecipeDto;
import studybackend.refrigeratorcleaner.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("select new studybackend.refrigeratorcleaner.dto.RecipeDto(recipe.foodName, recipe.ImgURL, recipe.ingredientList, recipe.recipeList) " +
            "from Recipe recipe " +
            "join recipe.member m " +
            "where recipe.member.email = :email " +
            "order by recipe.recipeId desc"
    )
    List<RecipeDto> findRecipeDtoList(@Param("email") String email);

    Recipe findByRecipeId(Long id);
}
