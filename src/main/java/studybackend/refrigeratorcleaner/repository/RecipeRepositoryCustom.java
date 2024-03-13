package studybackend.refrigeratorcleaner.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import studybackend.refrigeratorcleaner.dto.MyRecipeDto;

import java.util.List;

public interface RecipeRepositoryCustom {
    Page<MyRecipeDto> getRecipeDtoPage(@Param("socialId") String socialId, Pageable pageable);
}
