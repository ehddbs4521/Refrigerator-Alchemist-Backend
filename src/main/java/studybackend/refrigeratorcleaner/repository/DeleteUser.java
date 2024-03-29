package studybackend.refrigeratorcleaner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studybackend.refrigeratorcleaner.entity.Board;

public interface DeleteUser extends JpaRepository<Board, Long> {
        //탈퇴한 유저의 게시글을 모두 삭제
        void deleteByNickName(String nickName);
}
