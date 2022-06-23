package com.example.server.controller;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.StatusCode;
import com.example.server.model.dto.user.Vacation;
import com.example.server.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class VacationController {

    private final VacationService vacationService;
    @PostMapping("/regVacation")
    public ResponseEntity<StatusCode> addVacation(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                  @RequestBody Vacation vacation) {
        String username = principalDetails.getUsername();
        return vacationService.addVacation(username,vacation);
    }
}
