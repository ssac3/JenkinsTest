package com.example.server.controller;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class RejectController {
    @GetMapping("/getRole")
    public ResponseEntity<StatusCode> getRole(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println(principalDetails);
        String role =principalDetails.getUser().getRole();
        if(role.isEmpty()){
            return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(2).resMsg("실패").build());
        }else return new JsonResponse().send(HttpStatus.OK, StatusCode.builder().resCode(0).resMsg("확인").data(role).build());
    }
}
