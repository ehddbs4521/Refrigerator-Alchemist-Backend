package studybackend.refrigeratorcleaner.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@Setter
@Builder
//@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeDto {


    private String email ;
    @NotBlank(message = "제목을 입력해주세요.")
    private String  boardId ;


    @Builder
    public LikeDto(String email,String boardId){
        this.email = email;
        this.boardId = boardId;
    }
}
