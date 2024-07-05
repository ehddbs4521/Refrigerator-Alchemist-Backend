package studybackend.refrigeratorcleaner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studybackend.refrigeratorcleaner.entity.Board;

@Repository
public interface DeleteBoard extends JpaRepository<Board, Long> {
    void deleteById(Long id);
}
