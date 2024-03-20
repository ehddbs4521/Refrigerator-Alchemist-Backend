package studybackend.refrigeratorcleaner.redis.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@RedisHash
public class BlackList {

    @Id
    @Indexed
    private String socialId;

    private String accessToken;

    @TimeToLive
    private Long expiration;
}
