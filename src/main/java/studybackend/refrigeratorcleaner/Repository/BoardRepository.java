package studybackend.refrigeratorcleaner.Repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studybackend.refrigeratorcleaner.Entity.Board;
import studybackend.refrigeratorcleaner.Entity.BoardContent;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;
    public void saveBoard (Board b) {
            em.persist(b);
    }
    public  void saveBoardContent(BoardContent bC){
        em.persist(bC);
    }
    public List<Board> loginCheck (String identity, String pass){
        return em.createQuery("select m from Board m", Board.class).getResultList();
    }
    public  List<Board> getBoard(){
        return em.createQuery("select m from Board m", Board.class).getResultList();
    }
}
