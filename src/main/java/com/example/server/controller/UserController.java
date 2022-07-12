package com.example.server.controller;
import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.StatusCode;
import com.example.server.model.dto.user.User;
import com.example.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @GetMapping("/myView")
    public ResponseEntity<StatusCode> myView(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return userService.myView(principalDetails.getUsername());
    }
    @PostMapping("/updatePw")
    public ResponseEntity<StatusCode> updatepw(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                               @RequestBody User user)
    {
        return userService.updatepw(principalDetails.getUsername(), user);
    }
    @PostMapping("/myImgUpdate")
    public ResponseEntity<StatusCode> insertUserImage(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestPart(value = "image") MultipartFile multipartFile) {
        return userService.updateImg(principalDetails.getUsername(), multipartFile, "profile");
    }
}
