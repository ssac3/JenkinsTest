package com.example.server.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.admin.AdminMapper;
import com.example.server.model.dto.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;


@Service
@RequiredArgsConstructor // 하는 이유 질문
public class AdminService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AdminMapper adminMapper;
    private StatusCode statusCode;
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 사원번호 생성

    //사원등록
    @Transactional
//    public ResponseEntity<StatusCode> insertEmp(MultipartFile multipartFile, User user){
    public ResponseEntity<StatusCode> insertEmp(MultipartFile multipartFile, String dirName, User user){
        System.out.println("multipartFile = " + multipartFile);
        System.out.println("user.getUsername() = " + user.getUsername());

        String fileUrl = dirName +"/"+ user.getUsername() +"_"+multipartFile.getOriginalFilename();
        System.out.println("fileUrl = " + fileUrl);
        String uploadImageUrl = putS3(multipartFile, fileUrl, dirName); //s3 upload
        // upload method end
        String awsUrl = uploadImageUrl;
        String insertUrl = awsUrl + "/" +user.getUsername() + "_" + multipartFile.getOriginalFilename();
//        adminMapper.insertEmp(user.toInsertEntity(bCryptPasswordEncoder));
        adminMapper.insertEmp(user.toInsertEntity(bCryptPasswordEncoder, insertUrl));
        statusCode = StatusCode.builder().resCode(0).resMsg("사원등록을 성공했습니다").build();
        return new JsonResponse().send(HttpStatus.OK, statusCode);


        // username 유효성겁사(중복)

//        if(userInfo != null && !userInfo.equals("")){
//            System.out.println("ㅗㅗ");
//            // 업로드 시작
//            // 파일이름
////            String fileUrl = dirName +"/"+ username +"_"+multipartFile.getOriginalFilename();
//            String fileUrl = dirName +"/"+ user.getUsername() +"_"+multipartFile.getOriginalFilename();
//            System.out.println("fileUrl = " + fileUrl);
//            String uploadImageUrl = putS3(multipartFile, fileUrl, dirName); //s3 upload
//            String insertUrl = uploadImageUrl+ "/" +user.getUsername() + "_" + multipartFile.getOriginalFilename();
//            // 끝 업로드
////            adminMapper.insertEmp(user.toInsertEntity(bCryptPasswordEncoder));
//            adminMapper.insertEmp(user.toInsertEntity(bCryptPasswordEncoder));
//            statusCode = StatusCode.builder().resCode(0).resMsg("사원등록을 성공했습니다").build();
//        }else {
//            System.out.println("[ERR] 유효하지 않는 사용자 정보입니다.");
//            statusCode = StatusCode.builder().resCode(2).resMsg("유효하지 않는 사용자 정보입니다.").build();
//        }

    }
//    @Transactional
//    public ResponseEntity<StatusCode> insertEmp(String userInfo, User user){
//        //username 유효성겁사(중복)
//        if(userInfo != null && !userInfo.equals("")){
//            adminMapper.insertEmp(user.toInsertEntity(bCryptPasswordEncoder));
//            statusCode = StatusCode.builder().resCode(0).resMsg("사원등록을 성공했습니다").build();
//        }else {
//            System.out.println("[ERR] 유효하지 않는 사용자 정보입니다.");
//            statusCode = StatusCode.builder().resCode(2).resMsg("유효하지 않는 사용자 정보입니다.").build();
//        }
//        return new JsonResponse().send(HttpStatus.OK, statusCode);
//    }

    // 사원리스트정보
    @Transactional
    public ResponseEntity<StatusCode> viewEmp(String userInfo, User user) {
        if(userInfo != null && !userInfo.equals("")){
            List<User> empList = adminMapper.viewEmp(user);
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();

            Object data = empList.stream().map(value -> {
                map.put("username", value.getUsername());
                map.put("name", value.getName());
                map.put("email", value.getEmail());
                map.put("gender", value.getGender());
                map.put("position", value.getPosition());
                map.put("workingStatus", value.getWorkingStatus());
                map.put("img", value.getImg());
                map.put("role", value.getRole());
                map.put("qrPath", value.getQrPath());
                map.put("depId", value.getDepId());
                map.put("dName", value.getDepartment());
                map.put("location", value.getLocation());
                map.put("createdAt", value.getCreatedAt());
                return map;
            });
            statusCode = StatusCode.builder().resCode(0).data(data).resMsg("사원조회를 성공했습니다").build();
        }else {
            System.out.println("[ERR] 유효하지 않는 사용자 정보입니다.");
            statusCode = StatusCode.builder().resCode(2).resMsg("유효하지 않는 사용자 정보입니다.").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    //사원삭제
    @Transactional
    public ResponseEntity<StatusCode> deleteEmp(String userInfo, User user) {
        if(userInfo != null && !userInfo.equals("")){
            System.out.println("사원삭제");
            adminMapper.deleteEmp(user);
            statusCode = StatusCode.builder().resCode(0).resMsg("사원삭제를 성공했습니다").build();
        }else {
            System.out.println("[ERR] 유효하지 않는 사용자 정보입니다.");
            statusCode = StatusCode.builder().resCode(2).resMsg("유효하지 않는 사용자 정보입니다.").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    // 사원수정
    public ResponseEntity<StatusCode> updateEmp(String userInfo, User user) {
        if(userInfo != null && !userInfo.equals("")){
            System.out.println("userInfo.getName() = " + userInfo);
            System.out.println(user.toString());
            adminMapper.updateEmp(User.builder().
                    name(user.getName())
                    .gender(user.getGender())
                    .img(user.getImg())
                    .depId(user.getDepId())
                    .position(user.getPosition())
                    .role(user.getRole())
                    .qrPath(user.getQrPath())
                    .build()
            );
            System.out.println("UPDATE확인111"+user.getName());
            System.out.println("UPDATE확인222"+user);
            System.out.println("UPDATE확인333"+user.getGender());
            System.out.println("UPDATE개수??   :    adminMapper.updateEmp(user) = " + adminMapper.updateEmp(user));
            statusCode = StatusCode.builder().resCode(0).resMsg("사원수정을 성공했습니다").build();
        }else {
            System.out.println("[ERR] 유효하지 않는 사용자 정보입니다.");
            statusCode = StatusCode.builder().resCode(2).resMsg("유효하지 않는 사용자 정보입니다.").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    // S3
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
    /*public void remove(String fileName) {
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
    }*/
}

