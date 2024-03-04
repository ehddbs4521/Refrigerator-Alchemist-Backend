package studybackend.refrigeratorcleaner.oauth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {
    KAKAO("KAKAO"), NAVER("NAVER"), GOOGLE("GOOGLE"), Refrigerator_Cleaner("Refrigerator-Cleaner");

    private final String key;
}
