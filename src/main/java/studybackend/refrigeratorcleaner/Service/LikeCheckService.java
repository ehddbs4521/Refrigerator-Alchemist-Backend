package studybackend.refrigeratorcleaner.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studybackend.refrigeratorcleaner.Entity.LikeCheck;
import studybackend.refrigeratorcleaner.Repository.LikeCheckRepository;
import studybackend.refrigeratorcleaner.dto.LikeDto;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeCheckService {
    private final LikeCheckRepository likeRepo;

    public List<LikeCheck> verifyDoubleLike(String nickName,String boardId){
        return likeRepo.verifyDoubleLike(nickName,boardId);
    }
    public void  logLikeCheck(LikeCheck l) {
        likeRepo.logLikeCheck(l);
    }
    public void delLikeCheck(LikeCheck l) {likeRepo.delLikeCheck(l);}
    public List<LikeCheck> getMyLikeTitle(String nickName){
        return likeRepo.getMyLikeTitle(nickName);
    }
    //    public  List<Board> getMyLikeList (String nickName,String title) {
//        return likeRepo.getMyLikeList(nickName,title);
//    }
    public LikeCheck getLikeCheck(LikeDto l){
        LikeCheck  like =  LikeCheck.builder().
                nickName(l.getNickName()).
                boardId(l.getBoardId()).
                build();
        return like;
    }
}
