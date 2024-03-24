package studybackend.refrigeratorcleaner.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "like_check")
@Getter
@Builder
//@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeCheck { //한 사람이 하나의 게시글에 중복해서 '좋아요'를 누르지 못하게 체크하는 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nick_name") //별명
    private String nickName ;

    @Column(name = "board_id") //게시글 제목
    private String  boardId ;



    @Builder
    public  LikeCheck (String nickName,String boardId) {
        this.nickName = nickName;
        this.boardId = boardId;
    }
}
