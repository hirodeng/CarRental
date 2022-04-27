package com.carrental.bookingservice.modules.user.controller;

import com.carrental.bookingservice.modules.user.controller.req.SignInReq;
import com.carrental.bookingservice.modules.user.controller.req.SignUpReq;
import com.carrental.bookingservice.common.BaseResponse;
import com.carrental.bookingservice.modules.user.entity.User;
import com.carrental.bookingservice.modules.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public BaseResponse<String> signUp(@RequestBody SignUpReq req) {
        log.info("sign up req={}", req.toString());
        userService.signUp(req);
        return new BaseResponse<>(req.getName());
    }

    @PostMapping("/signin")
    public BaseResponse<String> signIn(@RequestBody SignInReq req) {
        userService.signIn(req);
        return new BaseResponse<>(req.getName());
    }

}
