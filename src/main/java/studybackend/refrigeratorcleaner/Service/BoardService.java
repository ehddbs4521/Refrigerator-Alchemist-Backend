package studybackend.refrigeratorcleaner.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studybackend.refrigeratorcleaner.Entity.Board;
import studybackend.refrigeratorcleaner.Entity.BoardContent;
import studybackend.refrigeratorcleaner.Repository.BoardRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
        private final BoardRepository bRepository ;
        public void saveBoard(Board b){
                bRepository.saveBoard(b);
        }
        public  void saveBoardContent(BoardContent bC) {bRepository.saveBoardContent(bC);}
        public List<Board> getBoard() {
                return bRepository.getBoard();
        }
 }
