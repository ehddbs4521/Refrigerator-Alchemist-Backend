package studybackend.refrigeratorcleaner.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column
    private  Long postId;

    @Column
    private String email;



    @Builder
    public Report(Long postId,String email){
        this.postId = postId;
        this.email = email;
    }

}
