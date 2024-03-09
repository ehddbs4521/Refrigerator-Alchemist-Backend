package studybackend.refrigeratorcleaner.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studybackend.refrigeratorcleaner.Entity.Board;
import studybackend.refrigeratorcleaner.Entity.User;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class MyPageRepository {
    private  final EntityManager em;

    public List<User> getUser(String nickName){
        return em.createQuery("select m from User m where m.nickName =: nickName", User.class)
                .setParameter("nickName",nickName).getResultList();
    }
    public void updateUser(User u){
        em.merge(u);
    }

}
