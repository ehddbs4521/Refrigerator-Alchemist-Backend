package studybackend.refrigeratorcleaner.service;


import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

//@Service
//@Transactional
//@RequiredArgsConstructor
//public class ImageService {
////
//    private final AmazonS3Client amazonS3Client;
//
//    @Value("${application.bucket.name}")
//    private String bucketName;
//
//    //이미지 s3에 저장하고 이미지 url 반환
//    public String uploadImage(InputStream in, String keyName) throws IOException {
//        //URL에서 이미지 다운로드
//
//        //InputStream in = url.openStream();
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentType("image/jpg");
//
//        //S3에 업로드
//        amazonS3Client.putObject(new PutObjectRequest(bucketName, keyName, in, metadata));
//
//        //S3에 업로드된 이미지의 url을 반환.
//        return amazonS3Client.getUrl(bucketName, keyName).toString();
//    }
//
//}

@Service
public class ImageService {

    @Autowired
    private AmazonS3 amazonS3; // AWS S3 클라이언트

    private final String bucketName = "refrigerator-board"; // AWS S3 버킷 이름

    public String uploadImage(InputStream inputStream, String fileName) throws IOException {
        // AWS S3에 저장될 파일 이름 생성
        String key = generateKey(fileName);

        // 이미지 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpg"); // 이미지 타입에 따라 적절히 설정

        // 이미지를 S3에 업로드
        amazonS3.putObject(new PutObjectRequest(bucketName, key, inputStream, metadata));

        // 업로드된 이미지의 URL 반환
        return amazonS3.getUrl(bucketName, key).toString();
    }

    public String saveImage (InputStream inputStream, String fileName, MultipartFile image) throws IOException {
        // AWS S3에 저장될 파일 이름 생성
        String key = generateKey(fileName);

        // 이미지 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(image.getSize());
        metadata.setContentType("image/jpg"); // 이미지 타입에 따라 적절히 설정

        // 이미지를 S3에 업로드
        amazonS3.putObject(new PutObjectRequest(bucketName, key, inputStream, metadata));

        // 업로드된 이미지의 URL 반환
        return amazonS3.getUrl(bucketName, key).toString();
    }
    private String generateKey(String fileName) {
        // UUID를 사용하여 고유한 파일 이름 생성
        String uniqueID = UUID.randomUUID().toString();
        // 파일 이름과 UUID를 결합하여 AWS S3에 저장될 키 생성
        return fileName + "-" + uniqueID;
    }
}