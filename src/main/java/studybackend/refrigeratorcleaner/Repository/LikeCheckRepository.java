package studybackend.refrigeratorcleaner.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studybackend.refrigeratorcleaner.Entity.Board;
import studybackend.refrigeratorcleaner.Entity.LikeCheck;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeCheckRepository {
    private final EntityManager em;
    public List<LikeCheck> verifyDoubleLike(String nickName,String title,String clickerName) {
        return em.createQuery("select m from LikeCheck m where " +
                        "m.nickName =: nickName and  m.title =: title and m.clickerName =: clickerName", LikeCheck.class)
                .setParameter("nickName",nickName)
                .setParameter("title",title)
                .setParameter("clickerName",clickerName).getResultList();
    }
    public void logLikeCheck(LikeCheck l){
        em.persist(l);
    }
}
