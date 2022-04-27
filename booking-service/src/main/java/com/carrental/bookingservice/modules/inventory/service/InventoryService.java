package com.carrental.bookingservice.modules.inventory.service;

import com.carrental.bookingservice.modules.inventory.controller.req.QueryAvailableCarsReq;
import com.carrental.bookingservice.modules.inventory.entity.Inventory;
import com.carrental.bookingservice.modules.inventory.repository.InventoryRepository;
import com.carrental.bookingservice.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    private static final long ONE_DAY_IN_MS = 1000 * 60 * 60 * 24;
    public static long MAX_DAYS_OF_QUERY_PERIOD = 90;

    public List<Inventory> queryAvailableCars(QueryAvailableCarsReq req) {
        Date now = TimeUtil.getToday();

        if (req.getEndDate().before(now)) {
            throw new InvalidParameterException("Invalid end date");
        }

        if ((req.getEndDate().getTime() - now.getTime()) > MAX_DAYS_OF_QUERY_PERIOD * ONE_DAY_IN_MS) {
            throw new InvalidParameterException("Invalid end date");
        }

        if (req.getStartDate().before(now)) {
            req.setStartDate(now);
        }

        if (req.getCarModel() == null) {
            return inventoryRepository.findByDateBetween(req.getStartDate(), req.getEndDate());
        } else {
            return inventoryRepository.findByCarModelAndDateBetween(
                    req.getCarModel(), req.getStartDate(), req.getEndDate()
            );
        }

    }

    public void reduceInventory(int num, String carModel, Date startDate, Date endDate) throws Exception {
        List<Inventory> inventories = inventoryRepository.findByCarModelAndDateBetween(carModel, startDate, endDate);
        if (inventories.isEmpty()) {
            throw new Exception("Error while reducing inventory: no inventory");
        }
        for (Inventory inventory : inventories) {
            if (inventory.getNumInStock() < num) {
                throw new Exception("Error while reducing inventory: too few cars in stock");
            }
            inventory.setNumInStock(inventory.getNumInStock() - num);
        }
        inventoryRepository.saveAll(inventories);
    }

    public void increaseInventory(int num, String carModel, Date startDate, Date endDate) {
        List<Inventory> inventories = inventoryRepository.findByCarModelAndDateBetween(carModel, startDate, endDate);
        for (Inventory inventory : inventories) {
            inventory.setNumInStock(inventory.getNumInStock() + num);
        }
        inventoryRepository.saveAll(inventories);
    }
}
