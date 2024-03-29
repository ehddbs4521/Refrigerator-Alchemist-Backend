package studybackend.refrigeratorcleaner.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "provider_name")
    private String providerName;

    @Builder
    public Member(Long id, String email, String password, String nickName, String profileUrl, String providerName) {
        this.id = id;
        this.email = email;this.password = password;
        this.nickName = nickName;
        this.profileUrl = profileUrl;
        this.providerName = providerName;
    }

}
