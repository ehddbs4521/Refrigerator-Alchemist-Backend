package studybackend.refrigeratorcleaner.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "like_check")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeCheck { //한 사람이 하나의 게시글에 중복해서 '좋아요'를 누르지 못하게 체크하는 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nick_name") //별명
    private String nickName ;

    @Column(name = "title") //게시글 제목
    private String  title ;

    @Column(name = "clicker_name") //좋아요 누른 사람
    private String clickerName;
}
