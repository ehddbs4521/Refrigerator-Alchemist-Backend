package studybackend.refrigeratorcleaner.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "board")
@Getter
//@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@NoArgsConstructor
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

    @Column(name = "image_url")
    private String imageUrl;
    @Builder
    public Board(String email,String nickName,String title,String texts,int likeCount,String imageUrl){
        this.email = email;
        this.nickName = nickName;
        this.title = title;
        this.texts = texts;
        this.likeCount = likeCount;
        this.imageUrl  = imageUrl;
    }
}
