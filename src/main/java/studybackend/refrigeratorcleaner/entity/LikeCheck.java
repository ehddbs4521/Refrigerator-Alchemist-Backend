package studybackend.refrigeratorcleaner.entity;

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

    @Column(name = "email") //이메일
    private String email ;

    @Column(name = "board_id") //게시글 제목
    private String  boardId ;



    @Builder
    public  LikeCheck (String email,String boardId) {
        this.email = email;
        this.boardId = boardId;
    }
}
