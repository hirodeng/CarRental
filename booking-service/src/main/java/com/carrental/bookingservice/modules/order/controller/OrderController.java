package com.carrental.bookingservice.modules.order.controller;

import com.carrental.bookingservice.common.BaseResponse;
import com.carrental.bookingservice.modules.order.controller.req.CreateOrderReq;
import com.carrental.bookingservice.modules.order.entity.Order;
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
        return new BaseResponse<>(orderService.queryOrders(user));
    }

    @PostMapping("/")
    public BaseResponse<Order> createOrder(@AuthenticationPrincipal User user, @RequestBody CreateOrderReq req) throws Exception {
        return new BaseResponse<>(orderService.createOrder(user, req));
    }

    @PostMapping("/{orderId}/pay")
    public BaseResponse<Order> payOrder(@AuthenticationPrincipal User user, @PathVariable Long orderId) throws Exception {
        return new BaseResponse<>(orderService.payOrder(user, orderId));
    }

    @PostMapping("/{orderId}/cancel")
    public BaseResponse<Order>  cancelOrder(@AuthenticationPrincipal User user, @PathVariable Long orderId) throws Exception {
        return new BaseResponse<>(orderService.cancelOrder(user, orderId));
    }

    @PostMapping("/{orderId}/complete")
    public BaseResponse<Order>  completeOrder(@AuthenticationPrincipal User user, @PathVariable Long orderId) throws Exception {
        return new BaseResponse<>(orderService.completeOrder(user, orderId));
    }
}
