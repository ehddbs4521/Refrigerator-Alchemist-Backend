package studybackend.refrigeratorcleaner.Service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3Client amazonS3Client;

    @Value("${application.bucket.name}")
    private String bucketName;

    //이미지 s3에 저장하고 이미지 url 반환
    public String uploadImage(InputStream in, String keyName) throws IOException {
        //URL에서 이미지 다운로드

        //InputStream in = url.openStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpg");

        //S3에 업로드
        amazonS3Client.putObject(new PutObjectRequest(bucketName, keyName, in, metadata));

        //S3에 업로드된 이미지의 url을 반환.
        return amazonS3Client.getUrl(bucketName, keyName).toString();
    }

}