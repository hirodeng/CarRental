package com.carrental.bookingservice.modules.order.controller;

import com.carrental.bookingservice.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping("/")
    public BaseResponse queryOrders() {
        return BaseResponse.success();
    }

    @PostMapping("/")
    public BaseResponse createOrder() {
        return BaseResponse.success();
    }

    @PostMapping("/{id}/pay")
    public BaseResponse payOrder() {
        return BaseResponse.success();
    }

    @PostMapping("/{id}/cancel")
    public BaseResponse cancelOrder() {
        return BaseResponse.success();
    }

    @PostMapping("/{id}/complete")
    public BaseResponse completeOrder() {
        return BaseResponse.success();
    }
}
