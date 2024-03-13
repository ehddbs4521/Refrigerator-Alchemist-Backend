package studybackend.refrigeratorcleaner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studybackend.refrigeratorcleaner.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

    boolean existsByRefreshToken(String refreshToken);

}
