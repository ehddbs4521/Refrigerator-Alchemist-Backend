package studybackend.refrigeratorcleaner.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "board_content")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "email")
    private String email;

    @Column(name = "texts") //게시글 내용
    private String texts;

//    @Builder
//    public BoardContent(Long id,String nickName, String email,String content){
//        this.id = id;
//        this.nickName = nickName;
//        this.email = email;
//        this.content = content;
//    }
}
