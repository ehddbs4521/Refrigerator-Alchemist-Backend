package studybackend.refrigeratorcleaner.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import studybackend.refrigeratorcleaner.redis.entity.BlackList;

@Repository
public interface BlackListRepository extends CrudRepository<BlackList, String> {

    boolean existsByAccessToken(String accessToken);
}
