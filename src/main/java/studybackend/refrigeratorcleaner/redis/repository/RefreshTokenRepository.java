package studybackend.refrigeratorcleaner.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import studybackend.refrigeratorcleaner.redis.entity.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
