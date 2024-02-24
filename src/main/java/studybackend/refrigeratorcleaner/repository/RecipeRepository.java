package studybackend.refrigeratorcleaner.repository;

import studybackend.refrigeratorcleaner.dto.MyRecipeDto;
import studybackend.refrigeratorcleaner.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("select new studybackend.refrigeratorcleaner.dto.MyRecipeDto(recipe.recipeId, recipe.foodName, recipe.ingredientList) " +
            "from Recipe recipe " +
            "join recipe.member m " +
            "where recipe.member.email = :email " +
            "order by recipe.recipeId desc"
    )
    List<MyRecipeDto> findRecipeDtoList(@Param("email") String email);

    Recipe findByRecipeId(Long id);
}
