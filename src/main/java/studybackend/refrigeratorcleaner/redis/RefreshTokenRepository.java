package studybackend.refrigeratorcleaner.redis;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public class RefreshTokenRepository implements CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token) {
        return null;
    }

    Optional<RefreshToken> findByAuthId(String authId) {
        return null;
    }

    @Override
    public <S extends RefreshToken> S save(S entity) {
        return null;
    }

    @Override
    public <S extends RefreshToken> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<RefreshToken> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<RefreshToken> findAll() {
        return null;
    }

    @Override
    public Iterable<RefreshToken> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(RefreshToken entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends RefreshToken> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
