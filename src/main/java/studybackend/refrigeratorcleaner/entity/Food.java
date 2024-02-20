package studybackend.refrigeratorcleaner.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "food")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "food_name", nullable = false)
    private String name;

    //좋아요 수
    @Column(name = "like_num")
    private int likeNum;

    @Builder
    public Food(String name, int likeNum){
        this.name = name;
        this.likeNum = likeNum;
    }
}
