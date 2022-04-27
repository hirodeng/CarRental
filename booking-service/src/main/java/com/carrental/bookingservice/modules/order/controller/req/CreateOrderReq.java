package com.carrental.bookingservice.modules.order.controller.req;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Data
public class CreateOrderReq {
    private String carModel;
    private Date startDate;
    private Date endDate;
}
