package studybackend.refrigeratorcleaner.Repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import studybackend.refrigeratorcleaner.Entity.Board;
import studybackend.refrigeratorcleaner.Entity.BoardContent;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;
    private final EntityManagerFactory emf;
    public void saveBoard (Board b) {
        em.persist(b);
    }
    @Transactional
    public  void updateBoard (Board b) {
        EntityManager e = emf.createEntityManager();
        EntityTransaction tx = e.getTransaction();
        tx.begin();
        em.merge(b);
        tx.commit();
        e.close();

    }
    public void deleteBoard(Board b)  {em.remove(b);}
    public  void saveBoardContent(BoardContent bC){
        em.persist(bC);
    }
    public  String boardSize() {
        return String.valueOf(em.createQuery("select m from Board m", Board.class).getResultList().size());
    }
    public List<Board> loginCheck (String identity, String pass){
        return em.createQuery("select m from Board m", Board.class).getResultList();
    }

    //유저가 올린 게시물 가져오기
    public Board getUserBoard(Long id) {return em.find(Board.class, id);}
    public List<Board> getSpecific(Long id){
        return em.createQuery("select m from Board m where m.id =: id", Board.class)
                .setParameter("id",id).getResultList();
    }
    public  List<Board> getBoard(int offset){
        return em.createQuery("select m from Board m order by id desc", Board.class)
                .setFirstResult(offset)
                .setMaxResults(6)  // 최대 결과 수 지정
                .getResultList();
    }
    public  List<Board> orderLikeCount(){ //좋아요 순으로 정렬해서 상위 3개
        return em.createQuery("select m from Board m order by  likeCount desc limit 3", Board.class).getResultList();
    }
    public List<Board> searchTitle(String title) {
        return em.createQuery("select m from Board m where m.title =: title", Board.class)
                .setParameter("title",title).getResultList();
    }
    public List<Board>myList(String nickName)
    {
        return em.createQuery("select m from Board m where m.nickName =: nickName", Board.class)
                .setParameter("nickName",nickName).getResultList();
    }


}
