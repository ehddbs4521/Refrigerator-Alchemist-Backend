package studybackend.refrigeratorcleaner.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studybackend.refrigeratorcleaner.Entity.Board;
import studybackend.refrigeratorcleaner.Entity.BoardContent;
import studybackend.refrigeratorcleaner.Repository.BoardRepository;
import studybackend.refrigeratorcleaner.Repository.DeleteUser;
import studybackend.refrigeratorcleaner.dto.BoardDto;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
        private final BoardRepository bRepository ;

        public void saveBoard(Board b){
                bRepository.saveBoard(b);
        }
        public void updateBoard (Board b) {bRepository.updateBoard(b);}
        public String boardSize() {return bRepository.boardSize();}
        public void deleteBoard(Board b) { bRepository.deleteBoard(b);}
        public List<Board> getSpecific(Long id) { return bRepository.getSpecific(id);}
        public  void saveBoardContent(BoardContent bC) {bRepository.saveBoardContent(bC);}
        public List<Board> getBoard(int offset) {
                return bRepository.getBoard(offset);
        }
        public List<Board> orderLikeCount() {return bRepository.orderLikeCount();}
        //제목으로 검색
        public List<Board> searchBoard (String title) {return  bRepository.searchTitle(title);}
        // 닉네임으로 게시글 삭제
        public List<Board> myList(String nickName) {
                return  bRepository.myList(nickName);
        }


        public Board  getBoarEntity(BoardDto b){
                Board board = Board.builder().
                        email(b.getEmail()).
                        nickName(b.getNickName()).
                        title(b.getTitle()).
                        texts(b.getTexts()).
                        likeCount(b.getLikeCount()).
                        build();
                return board;
        }
 }
