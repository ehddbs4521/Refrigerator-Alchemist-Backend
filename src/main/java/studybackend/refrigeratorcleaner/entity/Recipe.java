package studybackend.refrigeratorcleaner.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {

    @Id
    @Column(name = "recipe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @ManyToOne
    @JoinColumn(name = "user_socialId", referencedColumnName = "socialId")
    private User user;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "ingredient_str")
    private String ingredientStr;

    @Column(name = "recipe_str")
    private String recipeStr;

    @Builder
    public Recipe(Long recipeId, User user, String foodName, String ingredientStr, String recipeStr) {
        this.user = user;
        this.foodName = foodName;
        this.ingredientStr = ingredientStr;
        this.recipeStr = recipeStr;
    }
}
