package studybackend.refrigeratorcleaner.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    EXIST_USER_EMAIL_SOCIALTYPE(HttpStatus.CONFLICT, "RAU1","해당 socialType의 이메일이 이미 존재합니다."),
    EXIST_USER_NICKNAME(HttpStatus.CONFLICT, "RAU2","해당 닉네임이 이미 존재합니다."),
    NOT_EXIST_USER_EMAIL_SOCIALTYPE(HttpStatus.NOT_FOUND, "RAU3","해당 socialType의 이메일이 존재하지 않습니다."),
    NOT_EXIST_USER_EMAIL(HttpStatus.NOT_FOUND, "RAU4","이메일이 존재하지않습니다."),
    NOT_EXIST_USER_NICKNAME(HttpStatus.NOT_FOUND,"RAU5", "닉네임이 존재하지않습니다."),
    NOT_EXIST_USER_SOCIALID(HttpStatus.NOT_FOUND, "RAU6","socialId가 존재하지않습니다."),
    EXIST_ACCESSTOKEN_BLACKLIST(HttpStatus.CONFLICT,"RAT1","이미 로그아웃한 사용자입니다."),
    NOT_EXIST_REFRESHTOKEN(HttpStatus.NOT_FOUND,"RAT2","존재하지 않는 Refresh Token입니다."),
    NOT_VALID_REFRESHTOKEN(HttpStatus.BAD_REQUEST,"RAT3","유효하지 않은 Refresh토큰입니다."),
    NOT_EXTRACT_SOCIALID(HttpStatus.UNAUTHORIZED, "RAT4","토큰에서 socialId를 추출 할 수 없습니다.(잘못된 토큰)"),
    NOT_VALID_ACCESSTOKEN(HttpStatus.UNAUTHORIZED, "RAT5","Access토큰이 유효하지 않습니다."),
    WRONG_SIGNATURE_TOKEN(HttpStatus.UNAUTHORIZED, "RAT6","잘못된 JWT 서명입니다."),
    NOT_EQUAL_JSON(HttpStatus.BAD_REQUEST, "RAD1","data content-type이 json이 아닙니다."),
    NOT_EXTRACT_ACCESSTOKEN(HttpStatus.UNAUTHORIZED, "RAT7","토큰에서 AccessToken을 추출 할 수 없습니다.(잘못된 토큰)"),
    WRONG_CERTIFICATION_NUMBER(HttpStatus.BAD_REQUEST,"RAC1","인증번호가 틀렸습니다."),
    EXPIRE_CERTIFICATION_NUMBER(HttpStatus.BAD_REQUEST,"RAC2","인증번호가 만료되었습니다."),
    EXPIRE_TOKEN(HttpStatus.BAD_REQUEST,"RAT8","토큰이 만료되었습니다."),
    NOT_SURPPORTED_TOKEN(HttpStatus.BAD_REQUEST,"RAT9","지원되지 않는 JWT 토큰입니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST,"RAC3","비밀번호를 재입력해주세요"),
    NOT_REFRIGERATOR_SOCIALTYPE(HttpStatus.BAD_REQUEST,"RAC4","자체 서비스 회원가입 시 만든 비밀번호만 변경 가능합니다."),
    SEND_EMAIL_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"RAS1","이메일 전송을 실패했습니다."),
    NOT_EQUAL_EACH_TOKEN_SOCIALID(HttpStatus.BAD_REQUEST, "RAT10","로그인한 사용자의 Refresh Token이 아닙니다"),
    ILLEGAL_TOKEN(HttpStatus.BAD_REQUEST, "RAT11","JWT 토큰이 잘못되었습니다."),
    NO_INGREDIENT(HttpStatus.BAD_REQUEST, "RAR1","입력된 재료가 없습니다. 재료를 입력해 주세요."),//400(/recipe/recommend)
    NO_PERMISSION_FOR_RECIPE(HttpStatus.FORBIDDEN, "RAR2","해당 레시피에 대한 조회 권한이 없습니다."),//403(/recipe/myRecipe/{recipeId})
    SAVE_RECIPE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"RAR3","레시피 저장을 실패했습니다."),//500(/recipe/save)
    FAILED_TO_GET_RECIPE_LIST(HttpStatus.INTERNAL_SERVER_ERROR, "RAR4","레시피 목록 조회에 실패했습니다."),//500(/recipe/myRecipe)
    FAILED_TO_GET_DETAIL_RECIPE(HttpStatus.INTERNAL_SERVER_ERROR, "RAR5","상세 레시피 조회에 실패했습니다."),//500(/recipe/myRecipe/{recipeId})
    FAILED_TO_MAKE_RECIPE(HttpStatus.INTERNAL_SERVER_ERROR, "RAR6","추천 레시피 생성에 실패했습니다."),//500(/recipe/recommend)
    NO_EXIST_RECIPEID(HttpStatus.NOT_FOUND, "RAR7","해당 recipeId가 존재하지 않습니다."),//404(/recipe/myRecipe/{recipeId})
    NO_EXIST_RECOMMENDID(HttpStatus.NOT_FOUND, "RAR8","해당 recommendId가 존재하지 않습니다.");//404(/recipe/recommend/{recommendId})

    private final HttpStatus status;
    private final String code;
    private final String message;
}