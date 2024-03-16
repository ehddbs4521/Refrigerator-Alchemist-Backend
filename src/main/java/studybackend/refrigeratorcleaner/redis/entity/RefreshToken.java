package studybackend.refrigeratorcleaner.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@RedisHash(timeToLive = 60 * 60 * 24 * 14)
public class RefreshToken implements Serializable {

    @Id
    private String refreshToken;

    @Indexed
    private String socialId;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
