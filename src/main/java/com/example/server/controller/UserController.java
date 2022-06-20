package com.example.server.controller;

import com.example.server.config.jwt.JwtProperties;
import com.example.server.constants.StatusCode;
import com.example.server.model.dto.token.Token;
import com.example.server.model.dto.user.User;
import com.example.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/signup")
    public void saveUser(@RequestBody User user){
        userService.saveUser(user);
    }

    @GetMapping("/user")
    public void user(){
    }

    @GetMapping("/admin")
    public void admin(){
    }

//    @GetMapping("/api/myview")
//    public ResponseEntity<StatusCode> myview(@RequestHeader(JwtProperties.HEADER_STRING) String token){
//        return userService.myview(token);
//    }
    @GetMapping("/myview")
    public ResponseEntity<StatusCode> myview(HttpServletRequest request){
        return userService.myview(request);
    }

//    @GetMapping("/myview")
//    public User selectUser(@RequestBody User user){
//        return userService.selectUser(user.getUsername());
//    }


    @PostMapping("/updatepw")
    public ResponseEntity<StatusCode> updatepw(@RequestBody User user,
                                               @RequestHeader(JwtProperties.HEADER_STRING) String token){
        return userService.updatepw(user, token) ;
    }

//    @PostMapping("/delete")
//    public void delete(@RequestBody Token token){
//        userService.deleteById(token.getEMPLOYEE_username());
//    }

    @PostMapping("/logoutuser")
    public ResponseEntity<StatusCode> logout(HttpServletRequest request){
        return userService.logout(request);
    }
//    @RequestHeader(JwtProperties.HEADER_STRING) String token
}
