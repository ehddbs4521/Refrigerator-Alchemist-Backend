package studybackend.refrigeratorcleaner.oauth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {
    KAKAO("KAKAO"), NAVER("NAVER"), GOOGLE("GOOGLE"), Refrigerator_Alchemist("Refrigerator-Alchemist");

    private final String key;
}
