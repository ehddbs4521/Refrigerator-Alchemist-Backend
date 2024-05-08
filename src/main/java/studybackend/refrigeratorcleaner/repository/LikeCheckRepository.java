package studybackend.refrigeratorcleaner.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studybackend.refrigeratorcleaner.entity.LikeCheck;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class LikeCheckRepository {
    private final EntityManager em;
    public List<LikeCheck> verifyDoubleLike(String email, String boardId) {
        return em.createQuery("select m from LikeCheck m where " +
                        "m.email =: email and  m.boardId =: boardId", LikeCheck.class)
                .setParameter("email",email)
                .setParameter("boardId",boardId)
                .getResultList();
    }
    public void logLikeCheck(LikeCheck l){
        em.persist(l);
    }
    public void delLikeCheck (LikeCheck l) { em.remove(l);}
    public List<LikeCheck> getMyLikeTitle(String email) {
        return em.createQuery("select m from LikeCheck m where " +
                        "m.email =: email", LikeCheck.class)
                .setParameter("email",email).getResultList();
    }

}
