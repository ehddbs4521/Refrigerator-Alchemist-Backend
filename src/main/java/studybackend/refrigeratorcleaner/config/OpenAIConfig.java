package studybackend.refrigeratorcleaner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAIConfig {

    @Value("${openai.api.key}")
    String openaiApiKey;

    @Bean
    public RestTemplate template(){
        RestTemplate restTemplate = new RestTemplate();
        //add 메소드의 파라미터로는 인터셉터 객체가 들어가고, 인터셉터 객체를 생성할 땐 HttpRequest, body, ClientHttpRequestExecution가 필요.
        //ClientHttpRequestInterceptor 인터페이스를 람다식으로 구현.
        //excution은 ClientHttpRequestExecution 객체로, 요청을 실행시키는 execute 메소드 포함.
        restTemplate.getInterceptors().add((request, body, execution) -> {
            //요청 헤더에 다음을 추가한 후 request를 보냄.
            request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
            return execution.execute(request, body);
        });

        return restTemplate;
    }
}
