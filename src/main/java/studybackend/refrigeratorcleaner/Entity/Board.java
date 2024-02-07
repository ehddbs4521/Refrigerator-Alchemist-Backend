package studybackend.refrigeratorcleaner.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "board")
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email ;

    @Column(name = "nick_name") //별명
    private String nickName ;

    @Column(name = "title") //게시글 제목
    private String  title ;

    @Column(name = "texts")
    private String texts;

    @Column(name = "like_count") //게시글 좋아요 수
    private int likeCount ;

//    @Builder
//    public Board(Long id,String email,String nickName,String title,int likeCount){
//        this.id = id;
//        this.email = email;
//        this.nickName = nickName;
//        this.title = title;
//        this.likeCount = likeCount;
//    }
//    }
}
