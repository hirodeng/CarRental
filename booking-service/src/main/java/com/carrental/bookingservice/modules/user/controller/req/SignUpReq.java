package com.carrental.bookingservice.modules.user.controller.req;

import lombok.Data;

@Data
public class SignUpReq {
    private String name;
    private String password;
}
