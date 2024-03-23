package studybackend.refrigeratorcleaner.redis.repository;

import org.springframework.data.repository.CrudRepository;
import studybackend.refrigeratorcleaner.redis.entity.EmailAuthentication;

import java.util.Optional;

public interface EmailAuthenticationRepository extends CrudRepository<EmailAuthentication, String> {

    Optional<EmailAuthentication> findById(String id);

    boolean existsById(String id);
}
