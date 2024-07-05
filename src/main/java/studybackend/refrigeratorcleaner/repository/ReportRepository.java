package studybackend.refrigeratorcleaner.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studybackend.refrigeratorcleaner.entity.Report;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportRepository {
    private  final EntityManager em;

    public  void reportBoard(Report report){
        em.persist(report);
    }
    public List<Report> getReportCount(Long postId){
        return em.createQuery("SELECT m FROM Report m WHERE m.postId =:postId", Report.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    public List<Report> checkDupleReport(String email,Long postId){
        return em.createQuery("SELECT m FROM Report m WHERE m.email =:email and m.postId =:postId", Report.class)
                .setParameter("email", email)
                .setParameter("postId", postId)
                .getResultList();
    }

}
