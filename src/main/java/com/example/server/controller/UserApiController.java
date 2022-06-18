package com.example.server.controller;

import com.example.server.model.dto.user.User;
import com.example.server.service.UserService;
import com.example.server.model.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;


    @PostMapping("/signup")
    public void saveUser(@RequestBody UserSaveRequestDto userSaveRequestDto){
        userService.saveUser(userSaveRequestDto);
    }


    @GetMapping("/api/user")
    public void user(){
    }

    @GetMapping("/api/admin")
    public void admin(){

    }

    @GetMapping("/api/myview")
    public User selectUser(@RequestBody Map<String, String> model){
        return userService.selectUser(model.get("username"));
    }
}
