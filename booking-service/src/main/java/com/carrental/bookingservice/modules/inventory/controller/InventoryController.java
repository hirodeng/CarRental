package com.carrental.bookingservice.modules.inventory.controller;

import com.carrental.bookingservice.common.BaseResponse;
import com.carrental.bookingservice.modules.inventory.controller.req.QueryAvailableCarsReq;
import com.carrental.bookingservice.modules.inventory.entity.Inventory;
import com.carrental.bookingservice.modules.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/availableCars")
    public BaseResponse<List<Inventory>> queryAvailableCars(@RequestBody QueryAvailableCarsReq req) {
        return new BaseResponse<>(inventoryService.queryAvailableCars(req));
    }
}
