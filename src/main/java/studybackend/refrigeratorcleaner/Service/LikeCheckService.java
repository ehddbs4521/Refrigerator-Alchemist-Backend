package studybackend.refrigeratorcleaner.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studybackend.refrigeratorcleaner.Entity.LikeCheck;
import studybackend.refrigeratorcleaner.Repository.LikeCheckRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeCheckService {
    private final LikeCheckRepository likeRepo;
    public List<LikeCheck> verifyDoubleLike(String nickName,String title,String clickerName){
        return likeRepo.verifyDoubleLike(nickName,title,clickerName);
    }
    public void  logLikeCheck(LikeCheck l) {
        likeRepo.logLikeCheck(l);
    }
}
