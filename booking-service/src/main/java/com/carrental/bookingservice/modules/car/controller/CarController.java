package com.carrental.bookingservice.modules.car.controller;

import com.carrental.bookingservice.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cars")
public class CarController {

    @GetMapping("/")
    public BaseResponse queryAvailableCars() {
        return BaseResponse.success();
    }
}
