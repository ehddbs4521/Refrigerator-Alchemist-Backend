package studybackend.refrigeratorcleaner.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studybackend.refrigeratorcleaner.entity.User;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class MyPageRepository {
    private  final EntityManager em;

    public List<User> getUser(String email){
        return em.createQuery("select m from User m where m.email =: email", User.class)
                .setParameter("email",email).getResultList();
    }
    public void updateUser(User u){
        em.merge(u);
    }

}
