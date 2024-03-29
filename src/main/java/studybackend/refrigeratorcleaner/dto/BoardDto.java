package studybackend.refrigeratorcleaner.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardDto {
    private Long id;
    private String email ;

    private String nickName ;
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "본문을 입력해주세요.")
    private String texts;  //description

    private int likeCount ;
    private List<String> ingredients;
    private String imageUrl;

    @Builder
    public  BoardDto(Long id,String email, String nickName, String title,String texts,int likeCount,String imageUrl,List<String> ingredients){
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.title = title;
        this.texts = texts;
        this.likeCount = likeCount;
        this.ingredients = ingredients;
        this.imageUrl = imageUrl;
    }


}
