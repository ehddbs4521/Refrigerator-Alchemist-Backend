package studybackend.refrigeratorcleaner.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studybackend.refrigeratorcleaner.Entity.User;
import studybackend.refrigeratorcleaner.Repository.MyPageRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageRepository myPageRepository;
    public List<User> getUser(String nickName){
        return myPageRepository.getUser(nickName);
    }
    public void updateUser(User u){
        myPageRepository.updateUser(u);
    }
}
