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


    @PostMapping("/signUp")
    public void saveUser(@RequestBody User user){
        userService.saveUser(user);
    }

    @GetMapping("/user/main")
    public void user(){
        System.out.println("user접근");
    }

   /* @GetMapping("/admin")
    public void admin(){
        System.out.println("admin접근");
    }*/

    @GetMapping("/myView")
    public ResponseEntity<StatusCode> myView(HttpServletRequest request){
        return userService.myView(request);
    }


    @PostMapping("/updatePw")
    public ResponseEntity<StatusCode> updatepw(HttpServletRequest request,
                                               @RequestBody User user)
    {
        return userService.updatepw(request, user);
    }

    @PostMapping("/logoutUser")
    public ResponseEntity<StatusCode> logout(HttpServletRequest request){
        return userService.logout(request);
    }
}
