package studybackend.refrigeratorcleaner.dto.gptDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageRequest {
    private String model;
    private String prompt;
    private int n;
    private String size;

    @Builder
    public ImageRequest(String model, String prompt) {
        this.model = model;
        this.prompt = prompt;
        this.n = 1;
        this.size = "1024x1024";
    }
}
