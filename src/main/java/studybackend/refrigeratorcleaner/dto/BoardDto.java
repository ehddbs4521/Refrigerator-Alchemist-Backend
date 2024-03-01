package studybackend.refrigeratorcleaner.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import studybackend.refrigeratorcleaner.Entity.Board;

@Getter
//@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardDto {

    private String email ;

    private String nickName ;
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "본문을 입력해주세요.")
    private String texts;

    private int likeCount ;

    @Builder
    public  BoardDto(String email, String nickName, String title,String texts,int likeCount){
        this.email = email;
        this.nickName = nickName;
        this.title = title;
        this.texts = texts;
        this.likeCount = likeCount;
    }

//    public Board toEntity(){
//        return Board.builder().
//                title(title).
//        texts(texts).build();
//    }
}
