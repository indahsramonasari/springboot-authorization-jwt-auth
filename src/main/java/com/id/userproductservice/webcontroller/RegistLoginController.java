package com.id.userproductservice.webcontroller;

import com.id.userproductservice.domain.ResponseStatus;
import com.id.userproductservice.domain.UserRequest;
import com.id.userproductservice.domain.UserResponse;
import com.id.userproductservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class RegistLoginController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseStatus> registerUser(@RequestBody UserRequest userRequest) {
        log.info("UserRequest {} ",userRequest);
        return ResponseEntity.ok(userService.signUpUser(userRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponse> authenticateUser(@RequestBody UserRequest userRequest) {
        log.info("UserRequest {} ",userRequest);
        return ResponseEntity.ok(userService.loginUser(userRequest));
    }

}
