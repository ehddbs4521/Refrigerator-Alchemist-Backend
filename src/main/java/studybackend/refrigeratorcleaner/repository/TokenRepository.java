package studybackend.refrigeratorcleaner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studybackend.refrigeratorcleaner.entity.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByAccessToken(String accessToken);

    Optional<Token> findByRefreshToken(String refreshToken);

}
