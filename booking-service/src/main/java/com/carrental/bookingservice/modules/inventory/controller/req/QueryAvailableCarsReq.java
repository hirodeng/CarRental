package com.carrental.bookingservice.modules.inventory.controller.req;

import lombok.Data;

import java.util.Date;

@Data
public class QueryAvailableCarsReq {
    private Date startDate;
    private Date endDate;
    private String carModel;
}
