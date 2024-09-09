package com.rishmi5h.workoutTracker.controller;


import com.rishmi5h.workoutTracker.dto.AuthDTO;
import com.rishmi5h.workoutTracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDTO request){
        log.info("Register Info: {}", request.getUsername());
        try {
            String jwt = authService.register(request);
            if (jwt.chars().filter(ch -> ch == '.').count() != 2) {
                throw new IllegalArgumentException("Invalid JWT token");
            }
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + jwt);
            return ResponseEntity.ok().headers(headers).body(jwt);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDTO request) {
        log.info("Login Info: {}", request.getUsername());
        String jwt = authService.login(request);
        if (jwt.chars().filter(ch -> ch == '.').count() != 2) {
            throw new IllegalArgumentException("Invalid JWT token");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);
        return ResponseEntity.ok().headers(headers).body(jwt);
    }
}
