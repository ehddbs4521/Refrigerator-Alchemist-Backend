package studybackend.refrigeratorcleaner.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import studybackend.refrigeratorcleaner.dto.DetailRecipeDto;
import studybackend.refrigeratorcleaner.dto.MyRecipeDto;
import studybackend.refrigeratorcleaner.dto.RecipeSaveRequestDto;
import studybackend.refrigeratorcleaner.dto.gptDto.ImageRequest;
import studybackend.refrigeratorcleaner.dto.gptDto.ImageResponse;
import studybackend.refrigeratorcleaner.entity.Recipe;
import studybackend.refrigeratorcleaner.entity.User;
import studybackend.refrigeratorcleaner.error.CustomException;
import studybackend.refrigeratorcleaner.error.ErrorCode;
import studybackend.refrigeratorcleaner.repository.RecipeRepository;
import studybackend.refrigeratorcleaner.repository.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Value("${openai.model.image}")
    private String imageModel;

    @Value("${openai.api.url.image}")
    private String imageApiURL;

    @Autowired
    private RestTemplate template;

    //prompt에 맞는 이미지 생성
    public String image(String prompt){
        ImageRequest request = ImageRequest.builder()
                .model(imageModel)
                .prompt(prompt)
                .build();

        ImageResponse response = template.postForObject(imageApiURL, request, ImageResponse.class);

        return response.getData().get(0).getUrl();
    }

    //이미지 s3에 저장하고 이미지 url 반환
    public String uploadImage(String imageUrl, String keyName) throws IOException{
        //URL에서 이미지 다운로드
        URL url = new URL(imageUrl);
        InputStream in = url.openStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpg");

        //S3에 업로드
        amazonS3Client.putObject(new PutObjectRequest(bucketName, keyName, in, metadata));

        //S3에 업로드된 이미지의 url을 반환.
        return amazonS3Client.getUrl(bucketName, keyName).toString();
    }

    //List<String> -> 요소뒤에 /붙인 String으로 변환해서 반환
    public String listToString(List<String> strList){
        StringBuilder sb = new StringBuilder();

        for(String str : strList){
            sb.append(str+"/");
        }

        return sb.toString();
    }

    //요소뒤에 /붙인 String을 List<String>으로 변환해서 반환
    public List<String> StringToList(String str){
        List<String> newList = new ArrayList<>();

        for(String element : str.split("/")){
            newList.add(element.trim());
        }

        return newList;
    }

    //레시피 저장하기
    public Long saveRecipe(RecipeSaveRequestDto saveRequestDto, String socialId){
        User user = userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_EXIST_USER_SOCIALID));

        String S3imageUrl = "";

        //레시피 저장시 사진 생성이 참이면
        if(saveRequestDto.getImgFlag() == true) {
            //음식 사진 얻기
            String imgPrompt = listToString(saveRequestDto.getIngredients()) + "을 사용해서 만든 " + saveRequestDto.getFoodName() +"의 사진을 생성해줘.";
            String foodImgURL = image(imgPrompt);

            String fileName = UUID.randomUUID().toString().concat(saveRequestDto.getFoodName());


            //이미지 s3에 저장
            try {
                S3imageUrl = uploadImage(foodImgURL, fileName);
            } catch (IOException e) {
                throw new IllegalArgumentException("이미지를 저장하는 중 에러가 발생하였습니다.");
            }
        }

        //recipeDto의 List<String> 형식 recipe를 String으로 변환
        Recipe recipe = Recipe.builder()
                .user(user)
                .foodName(saveRequestDto.getFoodName())
                .ingredientStr(listToString(saveRequestDto.getIngredients()))
                .recipeStr(listToString(saveRequestDto.getRecipe()))
                .imgURL(S3imageUrl)
                .build();

        recipeRepository.save(recipe);

        return recipe.getRecipeId();
    }

    @Transactional(readOnly = true)
    //레시피 목록 조회하기
    public Page<MyRecipeDto> getRecipeList(String socialId, Pageable pageable) {
        Page<MyRecipeDto> myRecipeDtoPage = recipeRepository.getRecipeDtoPage(socialId, pageable);

        return myRecipeDtoPage;
    }

    @Transactional(readOnly = true)
    //상세레시피 가져오기
    public DetailRecipeDto getDetailRecipe(Long recipeId){
        Recipe recipe = recipeRepository.findByRecipeId(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_EXIST_RECIPEID));
        DetailRecipeDto detailRecipeDto = DetailRecipeDto.builder()
                .ingredients(StringToList(recipe.getIngredientStr()))
                .recipe(StringToList(recipe.getRecipeStr()))
                .foodName(recipe.getFoodName())
                .imgURL(recipe.getImgURL()) //null 설정될 수 있음.
                .build();

        return detailRecipeDto;
    }

    //해당 유저가 조회할 수 있는 레시피인지.
    @Transactional(readOnly = true)
    public boolean validateRecipeUser(Long recipeId, String socialId){
        User user = userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_EXIST_USER_SOCIALID));
        Recipe recipe = recipeRepository.findByRecipeId(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_EXIST_RECIPEID));

        User savedMember = recipe.getUser();

        if(!StringUtils.equals(user.getSocialId(), savedMember.getSocialId())){
            return false;
        }

        return true;
    }
}
