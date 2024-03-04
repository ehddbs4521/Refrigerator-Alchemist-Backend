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
    @JoinColumn(name = "user_socialId", referencedColumnName = "social_id")
    private User user;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "ingredient_str")
    private String ingredientStr;

    @Column(name = "recipe_str")
    private String recipeStr;

    @Column(name = "img_url")
    private String imgURL;

    @Builder
    public Recipe(User user, String foodName, String ingredientStr, String recipeStr, String imgURL) {
        this.user = user;
        this.foodName = foodName;
        this.ingredientStr = ingredientStr;
        this.recipeStr = recipeStr;
        this.imgURL = imgURL;
    }
}
