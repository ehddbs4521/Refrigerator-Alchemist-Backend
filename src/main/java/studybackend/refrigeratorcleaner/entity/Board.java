package studybackend.refrigeratorcleaner.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "board")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "nick_name") // 별명
    private String nickName;

    @Column(name = "title") // 게시글 제목
    private String title;

    @Column(name = "texts")
    private String texts;

    @Column(name = "like_count") // 게시글 좋아요 수
    private int likeCount;

    @ElementCollection
    @Column(name = "ingredients")
    private List<String> ingredients;

    @Column(name = "image_url")
    private String imageUrl;

    public void updateBoard(String title, String texts, List<String> ingredients) {
        this.title = title;
        this.texts = texts;
        this.ingredients = ingredients;
    }

    public void onLikeCnt() {
        this.likeCount += 1;
    }

    public void offLikeCnt() {
        this.likeCount -= 1;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
