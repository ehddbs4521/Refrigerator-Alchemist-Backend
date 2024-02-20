package studybackend.refrigeratorcleaner.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "recipe")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {

    @Id
    @Column(name = "recipe_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recipeId;

    @ManyToOne
    @JoinColumn(name = "member_email", referencedColumnName = "email")
    private Member member;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "ingredient_list")
    private List<String> ingredientList;

    @Column(name = "recipe_list")
    private List<String> recipeList;

    @Column(name = "img_url")
    private String ImgURL;

    @Builder
    public Recipe(Member member, String foodName, List<String> ingredientList, List<String> recipeList, String imgURL) {
        this.member = member;
        this.foodName = foodName;
        this.ingredientList = ingredientList;
        this.recipeList = recipeList;
        ImgURL = imgURL;
    }
}
