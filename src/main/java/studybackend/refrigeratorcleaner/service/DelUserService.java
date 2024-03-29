package studybackend.refrigeratorcleaner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studybackend.refrigeratorcleaner.repository.DeleteUser;

@Service
@Transactional
@RequiredArgsConstructor
public class DelUserService {
    private final DeleteUser dRepo;
    public void deleteByNickName(String nickName) {
        dRepo.deleteByNickName(nickName);
    }
}
