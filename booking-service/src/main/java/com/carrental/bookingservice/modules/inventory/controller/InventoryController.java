package com.carrental.bookingservice.modules.inventory.controller;

import com.carrental.bookingservice.common.BaseResponse;
import com.carrental.bookingservice.modules.inventory.controller.req.QueryAvailableCarsReq;
import com.carrental.bookingservice.modules.inventory.entity.Inventory;
import com.carrental.bookingservice.modules.inventory.service.InventoryService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/availableCars")
    public BaseResponse<List<Inventory>> queryAvailableCars(@RequestBody QueryAvailableCarsReq req) {
        return new BaseResponse<>(inventoryService.queryAvailableCars(req));
    }
}
