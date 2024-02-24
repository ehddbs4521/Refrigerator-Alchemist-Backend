package studybackend.refrigeratorcleaner.service;

import studybackend.refrigeratorcleaner.dto.MyRecipeDto;
import studybackend.refrigeratorcleaner.dto.DetailRecipeDto;
import studybackend.refrigeratorcleaner.entity.Member;
import studybackend.refrigeratorcleaner.entity.Recipe;
import studybackend.refrigeratorcleaner.repository.MemberRepository;
import studybackend.refrigeratorcleaner.repository.RecipeRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final AmazonS3 s3Client;

    @Value("${application.bucket.name}")
    private String bucketName;

    //이미지 s3에 저장하고 이미지 url 반환
    public String uploadImage(String imageUrl, String keyName) throws IOException{
        //URL에서 이미지 다운로드
        URL url = new URL(imageUrl);
        InputStream in = url.openStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpg");

        //S3에 업로드
        s3Client.putObject(new PutObjectRequest(bucketName, keyName, in, metadata));

        //S3에 업로드된 이미지의 url을 반환.
        return s3Client.getUrl(bucketName, keyName).toString();
    }

    //레시피 저장하기
    public Long saveRecipe(DetailRecipeDto recipeDto, String email){
        Member member = memberRepository.findByEmail(email);
        String fileName = UUID.randomUUID().toString().concat(recipeDto.getFoodName());
        String S3imageUrl;

        try{
            S3imageUrl = uploadImage(recipeDto.getImgUrl(), fileName);
        }catch (IOException e){
            throw new IllegalArgumentException("이미지를 저장하는 중 에러가 발생하였습니다.");
        }

        Recipe recipe = Recipe.builder()
                .foodName(recipeDto.getFoodName())
                .ingredientList(recipeDto.getIngredients())
                .recipeList(recipeDto.getRecipe())
                .imgURL(S3imageUrl)
                .member(member)
                .build();

        recipeRepository.save(recipe);

        return recipe.getRecipeId();
    }

    //레시피 목록 조회하기
    public List<MyRecipeDto> getRecipeList(String email) {
        List<MyRecipeDto> myRecipeDtoList = recipeRepository.findRecipeDtoList(email);

        return myRecipeDtoList;
    }

    //상세레시피 가져오기
    public DetailRecipeDto getDetailRecipe(Long recipeId){
        Recipe recipe = recipeRepository.findByRecipeId(recipeId);
        DetailRecipeDto detailRecipeDto = DetailRecipeDto.builder()
                .ingredients(recipe.getIngredientList())
                .recipe(recipe.getRecipeList())
                .imgUrl(recipe.getImgURL())
                .foodName(recipe.getFoodName())
                .build();

        return detailRecipeDto;
    }
}
