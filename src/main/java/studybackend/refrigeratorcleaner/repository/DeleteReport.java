package studybackend.refrigeratorcleaner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studybackend.refrigeratorcleaner.entity.Report;

@Repository
public interface DeleteReport extends JpaRepository<Report, Long> {
    void deleteByPostId(Long postId);
}
