package studybackend.refrigeratorcleaner.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import studybackend.refrigeratorcleaner.dto.BoardDto;
import studybackend.refrigeratorcleaner.entity.Board;
import studybackend.refrigeratorcleaner.entity.BoardContent;
import studybackend.refrigeratorcleaner.entity.User;
import studybackend.refrigeratorcleaner.repository.BoardRepository;
import studybackend.refrigeratorcleaner.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
        private final BoardRepository bRepository ;
        private final UserRepository userRepository;
        public void saveBoard(Board b){
                bRepository.saveBoard(b);
        }
        public  void saveBoardContent(BoardContent bC) {bRepository.saveBoardContent(bC);}
        public void updateBoard (Board b) {bRepository.updateBoard(b);}
        public void saveBoarDto(BoardDto b){
                Board board = Board.builder().
                        id(b.getId()).
                        email(b.getEmail()).
                        nickName(b.getNickName()).
                        title(b.getTitle()).
                        texts(b.getTexts()).
                        likeCount(b.getLikeCount()).
                        ingredients(b.getIngredients()).
                        build();
                bRepository.saveBoard(board);
        }
        public User getUser(String nickName) { return
                bRepository.getUserByNickName(nickName);
        }
        public void deleteBoard(Long id) {
                bRepository.deleteBoard(id);
        }
        public String boardSize() {return bRepository.boardSize();}
        public BoardDto recentBoard() {
                Board board = bRepository.recentBoard();
                BoardDto boardDto = BoardDto.builder().
                        id(board.getId()).
                        email(board.getEmail()).
                        title(board.getTitle()).
                        nickName(board.getNickName()).
                        texts(board.getTexts()).
                        imageUrl(board.getImageUrl()).
                        likeCount(board.getLikeCount()).
                        ingredients(board.getIngredients()).
                        build();

                return boardDto;
        }
        public  Board getUserBoard(Long id) {

                return bRepository.getUserBoard(id);
        }

        public List<BoardDto> getSpecific(Long id) {
                List<Board> boards = bRepository.getSpecific(id);
                List<BoardDto> boadDtos = new ArrayList<>();
                for(Board board : boards){
                        BoardDto boardDto = BoardDto.builder().
                                id(board.getId()).
                                email(board.getEmail()).
                                title(board.getTitle()).
                                nickName(board.getNickName()).
                                texts(board.getTexts()).
                                imageUrl(board.getImageUrl()).
                                likeCount(board.getLikeCount()).
                                ingredients(board.getIngredients()).
                                build();
                        boadDtos.add(boardDto);
                }
                return boadDtos;
        }

        public List<BoardDto> getBoard(int offset) {
                List<Board> boards =bRepository.getBoard(offset);
                List<BoardDto> boadDtos = new ArrayList<>();
                for(Board board : boards){
                        BoardDto boardDto = BoardDto.builder().
                                id(board.getId()).
                                email(board.getEmail()).
                                title(board.getTitle()).
                                nickName(board.getNickName()).
                                texts(board.getTexts()).
                                imageUrl(board.getImageUrl()).
                                likeCount(board.getLikeCount()).
                                ingredients(board.getIngredients()).
                                build();
                        boadDtos.add(boardDto);
                }
                return boadDtos;
        }
        public List<BoardDto> orderLikeCount() {
                List<Board> boards = bRepository.orderLikeCount();
                List<BoardDto> boadDtos = new ArrayList<>();
                for(Board board : boards){
                        BoardDto boardDto = BoardDto.builder().
                                id(board.getId()).
                                email(board.getEmail()).
                                title(board.getTitle()).
                                nickName(board.getNickName()).
                                texts(board.getTexts()).
                                imageUrl(board.getImageUrl()).
                                likeCount(board.getLikeCount()).
                                ingredients(board.getIngredients()).
                                build();
                        boadDtos.add(boardDto);
                }
                return boadDtos;

        }
        //제목으로 검색
        public List<BoardDto> searchBoard (String title) {
                List<Board> boards = bRepository.searchTitle(title);
                List<BoardDto> boadDtos = new ArrayList<>();
                for(Board board : boards){
                        BoardDto boardDto = BoardDto.builder().
                                id(board.getId()).
                                email(board.getEmail()).
                                title(board.getTitle()).
                                nickName(board.getNickName()).
                                texts(board.getTexts()).
                                imageUrl(board.getImageUrl()).
                                likeCount(board.getLikeCount()).
                                ingredients(board.getIngredients()).
                                build();
                        boadDtos.add(boardDto);
                }
                return boadDtos;
        }
        // 닉네임으로 게시글 삭제
        public List<BoardDto> myList(String email) {
                List<Board> boards  =  bRepository.myList(email);
                List<BoardDto> boadDtos = new ArrayList<>();
                for(Board board : boards){
                        BoardDto boardDto = BoardDto.builder().
                                id(board.getId()).
                                email(board.getEmail()).
                                title(board.getTitle()).
                                nickName(board.getNickName()).
                                texts(board.getTexts()).
                                imageUrl(board.getImageUrl()).
                                likeCount(board.getLikeCount()).
                                ingredients(board.getIngredients()).
                                build();
                        boadDtos.add(boardDto);
                }
                return  boadDtos;
        }




        @Transactional(readOnly = true)
        public BoardDto getBoardDto(Long id){
                Board board = bRepository.getSpecific(id).get(0);
                BoardDto boardDto = BoardDto.builder().
                        id(board.getId()).
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
