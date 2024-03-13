package studybackend.refrigeratorcleaner.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import studybackend.refrigeratorcleaner.dto.MyRecipeDto;

import java.util.List;

@Repository
public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<MyRecipeDto> getRecipeDtoPage(String socialId, Pageable pageable) {

        // Recipe와 User를 조인하고 해당 socialId에 해당하는 User의 Recipe를 가져옴.
        List<MyRecipeDto> myRecipeDtos = entityManager.createQuery(
                        "SELECT NEW studybackend.refrigeratorcleaner.dto.MyRecipeDto(r.recipeId, r.foodName, r.ingredientStr) " +
                                "FROM Recipe r " +
                                "JOIN r.user u " +
                                "WHERE u.socialId = :socialId " +
                                "order by r.recipeId desc", MyRecipeDto.class)
                .setParameter("socialId", socialId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // Recipe 개수
        long total = myRecipeDtos.size();

        return new PageImpl<MyRecipeDto>(myRecipeDtos, pageable, total);
    }
}
