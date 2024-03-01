package studybackend.refrigeratorcleaner.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
//@Setter
@Builder
//@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeDto {


    private String nickName ;
    @NotBlank(message = "제목을 입력해주세요.")
    private String  title ;
    private String clickerName;

    @Builder
    public LikeDto(String nickName,String title,String clickerName){
        this.nickName = nickName;
        this.title = title;
        this.clickerName = clickerName;
    }
}
