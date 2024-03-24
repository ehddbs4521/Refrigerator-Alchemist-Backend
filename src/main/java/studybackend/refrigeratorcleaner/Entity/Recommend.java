package studybackend.refrigeratorcleaner.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recommend")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommend {

    @Id
    @Column(name = "recommend_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendId;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "ingredient_str")
    private String ingredientStr;

    @Column(name = "recipe_str")
    private String recipeStr;

    @Builder
    public Recommend(String foodName, String ingredientStr, String recipeStr) {
        this.foodName = foodName;
        this.ingredientStr = ingredientStr;
        this.recipeStr = recipeStr;
    }
}
