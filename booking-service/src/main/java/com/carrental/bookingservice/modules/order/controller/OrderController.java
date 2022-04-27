package com.carrental.bookingservice.modules.order.controller;

import com.carrental.bookingservice.common.BaseResponse;
import com.carrental.bookingservice.modules.order.controller.req.CreateOrderReq;
import com.carrental.bookingservice.modules.order.service.OrderService;
import com.carrental.bookingservice.modules.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public BaseResponse queryOrders(@AuthenticationPrincipal User user) {
        return new BaseResponse(orderService.queryOrders(user));
    }

    @PostMapping("/")
    public BaseResponse createOrder(@AuthenticationPrincipal User user, @RequestBody CreateOrderReq req) throws Exception {
        orderService.createOrder(user, req);
        return BaseResponse.success();
    }

    @PostMapping("/{orderId}/pay")
    public BaseResponse payOrder(@AuthenticationPrincipal User user, @PathVariable Long orderId) throws Exception {
        orderService.payOrder(user, orderId);
        return BaseResponse.success();
    }

    @PostMapping("/{orderId}/cancel")
    public BaseResponse cancelOrder(@AuthenticationPrincipal User user, @PathVariable Long orderId) throws Exception {
        orderService.cancelOrder(user, orderId);
        return BaseResponse.success();
    }

    @PostMapping("/{orderId}/complete")
    public BaseResponse completeOrder(@AuthenticationPrincipal User user, @PathVariable Long orderId) throws Exception {
        orderService.completeOrder(user, orderId);
        return BaseResponse.success();
    }
}
