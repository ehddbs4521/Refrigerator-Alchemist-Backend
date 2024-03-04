package studybackend.refrigeratorcleaner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import studybackend.refrigeratorcleaner.dto.MyRecipeDto;
import studybackend.refrigeratorcleaner.entity.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("select new studybackend.refrigeratorcleaner.dto.MyRecipeDto(recipe.recipeId, recipe.foodName, recipe.ingredientStr) " +
            "from Recipe recipe " +
            "join recipe.user u " +
            "where recipe.user.socialId = :socialId " +
            "order by recipe.recipeId desc"
    )
    List<MyRecipeDto> findRecipeDtoList(@Param("socialId") String socialId);

    Optional<Recipe> findByRecipeId(Long id);
}
