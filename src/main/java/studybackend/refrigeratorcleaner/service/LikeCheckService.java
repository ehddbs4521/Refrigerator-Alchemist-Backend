package studybackend.refrigeratorcleaner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import studybackend.refrigeratorcleaner.dto.LikeDto;
import studybackend.refrigeratorcleaner.entity.LikeCheck;
import studybackend.refrigeratorcleaner.repository.LikeCheckRepository;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class LikeCheckService {
    private final LikeCheckRepository likeRepo;

    public List<LikeCheck> verifyDoubleLike(String email, String boardId){
        return likeRepo.verifyDoubleLike(email,boardId);
    }
    public void  logLikeCheck(LikeCheck l) {
        likeRepo.logLikeCheck(l);
    }
    public void delLikeCheck(LikeCheck l) {likeRepo.delLikeCheck(l);}
    public List<LikeCheck> getMyLikeTitle(String email){
        return likeRepo.getMyLikeTitle(email);
    }
    //    public  List<Board> getMyLikeList (String nickName,String title) {
//        return likeRepo.getMyLikeList(nickName,title);
//    }
    public LikeCheck getLikeCheck(LikeDto l){
        LikeCheck  like =  LikeCheck.builder().
                email(l.getEmail()).
                boardId(l.getBoardId()).
                build();
        return like;
    }
}
