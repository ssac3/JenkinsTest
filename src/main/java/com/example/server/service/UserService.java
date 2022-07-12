package com.example.server.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.user.UserMapper;
import com.example.server.model.dto.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private StatusCode statusCode;
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String selectPw(String username) {
        return userMapper.findByUsername(Long.parseLong(username)).getPassword();
    }
    public ResponseEntity<StatusCode> updatepw(String username, User user){
        String pwCheck = selectPw(username);
        String nPw = user.getNPassword();
        System.out.println("user = " + user.getNPasswordCheck());
        if (bCryptPasswordEncoder.matches(user.getPassword(), pwCheck)){

            String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{8,32}$";
            Matcher matcher = Pattern.compile(pwPattern).matcher(nPw);
            if(!matcher.matches()){
                statusCode = StatusCode.builder().resCode(2)
                        .resMsg("비밀번호는 8~32자이어야 하며, 대/소문자, 숫자, 특수기호를 모두 포함해야 합니다.")
                        .build();
            }
            else if(user.getNPasswordCheck() != nPw) {
                statusCode = StatusCode.builder().resCode(3)
                        .resMsg("새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.")
                        .build();
            }
            else {
                String eNPw = bCryptPasswordEncoder.encode(nPw);
                System.out.println(eNPw);
                userMapper.updateByUsername(User.builder().username(Long.parseLong(username))
                        .password(eNPw).build());
                statusCode = StatusCode.builder().resCode(0).resMsg("비밀번호 수정 성공").build();
            }

        }
        else{
            statusCode = StatusCode.builder().resCode(1).resMsg("현재 비밀번호가 일치하지 않습니다..").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }
    public ResponseEntity<StatusCode> myView(String username){
        System.out.println(username);
        statusCode = StatusCode.builder().resCode(0).resMsg("회원 정보 조회 성공")
                .data(userMapper.myView(Long.parseLong(username))).build();
        System.out.println(statusCode.getData().toString());
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }
    public ResponseEntity<StatusCode> updateImg(String username, MultipartFile multipartFile, String dirName){

         if(userMapper.findByUsername(Long.parseLong(username)).getImg().equals("") || userMapper.findByUsername(Long.parseLong(username)).getImg().isEmpty()){
            String awsUrl = upload(multipartFile, dirName, username);
            String insertUrl = awsUrl + "/" +username + "_" + multipartFile.getOriginalFilename();
            userMapper.updateImg(User.builder().username(Long.parseLong(username)).img(insertUrl).build());
        }
        else {
            String img = userMapper.findByUsername(Long.parseLong(username)).getImg();
            String deleteImg = img.replace(amazonS3Client.getUrl(bucket,"").toString(), "");
            remove(deleteImg);
            String awsUrl = upload(multipartFile, dirName, username);
            String insertUrl = awsUrl + "/" +username + "_" + multipartFile.getOriginalFilename();
            userMapper.updateImg(User.builder().username(Long.parseLong(username)).img(insertUrl).build());
        }
        statusCode = StatusCode.builder().resCode(0).resMsg("이미지 업데이트 성공").build();
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }
    public String upload(MultipartFile multipartFile, String dirName, String username){
        String fileUrl = dirName +"/"+ username +"_"+multipartFile.getOriginalFilename(); // S3에 저장될 파일 이름
        System.out.println("fileUrl = " + fileUrl);
        String uploadImageUrl = putS3(multipartFile, fileUrl, dirName); //s3 upload
        return uploadImageUrl;
    }
    public String putS3(MultipartFile multipartFile, String fileName, String dirName){
        try {
            String contentType = multipartFile.getContentType();
            long contentLength = multipartFile.getSize();

            InputStream is = multipartFile.getInputStream();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);
            objectMetadata.setContentLength(contentLength);

            amazonS3Client.putObject(new PutObjectRequest(this.bucket, fileName, is, objectMetadata));
            System.out.println(String.format("[%s] upload complete", fileName));
        }catch (AmazonS3Exception e){
            e.getMessage();
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return amazonS3Client.getUrl(bucket, dirName).toString();
    }
        public void remove(String fileName) {
        try {
            System.out.println("fileName : "+fileName);
            //Delete 객체 생성
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileName);
            //Delete
            amazonS3Client.deleteObject(deleteObjectRequest);
            System.out.println(String.format("[%s] delete complete", fileName));
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }
}
