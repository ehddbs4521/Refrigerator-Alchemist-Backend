package studybackend.refrigeratorcleaner.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studybackend.refrigeratorcleaner.entity.User;
import studybackend.refrigeratorcleaner.repository.MyPageRepository;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageRepository myPageRepository;
    public List<User> getUser(String email){
        return myPageRepository.getUser(email);
    }
    public void updateUser(User u){
        myPageRepository.updateUser(u);
    }
}
