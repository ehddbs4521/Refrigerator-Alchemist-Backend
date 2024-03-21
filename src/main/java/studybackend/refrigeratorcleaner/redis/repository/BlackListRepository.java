package studybackend.refrigeratorcleaner.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import studybackend.refrigeratorcleaner.redis.entity.BlackList;

import java.util.Optional;

@Repository
public interface BlackListRepository extends CrudRepository<BlackList, String> {

    boolean existsByAccessToken(String accessToken);

    Optional<BlackList> findBySocialId(String socialId);
}
