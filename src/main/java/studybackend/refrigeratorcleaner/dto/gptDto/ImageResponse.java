package studybackend.refrigeratorcleaner.dto.gptDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {
    private List<Data> data;

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data{
        private String revised_prompt;
        private String url;
    }
}
