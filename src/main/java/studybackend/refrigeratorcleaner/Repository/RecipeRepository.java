package studybackend.refrigeratorcleaner.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import studybackend.refrigeratorcleaner.dto.MyRecipeDto;
import studybackend.refrigeratorcleaner.Entity.Recipe;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{

    @Query("select new studybackend.refrigeratorcleaner.dto.MyRecipeDto(recipe.recipeId, recipe.foodName, recipe.ingredientStr) " +
            "from Recipe recipe " +
            "join recipe.user u " +
            "where recipe.user.socialId = :socialId " +
            "order by recipe.recipeId desc"
    )
    List<MyRecipeDto> findRecipeDtoList(@Param("socialId") String socialId);

    Optional<Recipe> findByRecipeId(Long id);
}
