package com.example.server.controller;

import com.example.server.constants.StatusCode;
import com.example.server.service.CommuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
public class CommuteController {
    private final CommuteService commuteService;
    @PostMapping("/commuteRecord")
    public ResponseEntity<StatusCode> commuteRecord(@RequestBody Map<String,String> username){
        return commuteService.commute(username.get("username"));
    }
}

