package studybackend.refrigeratorcleaner.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studybackend.refrigeratorcleaner.Entity.Board;
import studybackend.refrigeratorcleaner.Entity.BoardContent;
import studybackend.refrigeratorcleaner.Repository.BoardRepository;
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
        public  Board getUserBoard(Long id) {return bRepository.getUserBoard(id);}
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


        public void saveBoarDto(BoardDto b){
                Board board = Board.builder().
                        email(b.getEmail()).
                        nickName(b.getNickName()).
                        title(b.getTitle()).
                        texts(b.getTexts()).
                        likeCount(b.getLikeCount()).
                        ingredients(b.getIngredients()).
                        build();
                bRepository.saveBoard(board);
        }

        @Transactional(readOnly = true)
        public BoardDto getBoardDto(Long id){
                Board board = bRepository.getSpecific(id).get(0);
               BoardDto boardDto = BoardDto.builder().
                       nickName(board.getNickName()).
                       email(board.getEmail()).
                       texts(board.getTexts()).
                       title(board.getTitle()).
                       ingredients(board.getIngredients()).
                       imageUrl(board.getImageUrl()).
                       likeCount(board.getLikeCount()).
                       build();
               return boardDto;
        }
}
