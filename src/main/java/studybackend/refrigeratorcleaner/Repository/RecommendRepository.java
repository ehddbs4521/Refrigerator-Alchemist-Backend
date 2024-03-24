package studybackend.refrigeratorcleaner.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studybackend.refrigeratorcleaner.Entity.Recommend;

import java.util.Optional;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {

    Optional<Recommend> findByRecommendId(Long id);
}
