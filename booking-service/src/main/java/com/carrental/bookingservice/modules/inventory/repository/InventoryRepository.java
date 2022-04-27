package com.carrental.bookingservice.modules.inventory.repository;

import com.carrental.bookingservice.modules.inventory.entity.Inventory;
import com.carrental.bookingservice.modules.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {
    List<Inventory> findByDateBetween(Date startDate, Date endDate);

    List<Inventory> findByCarModelAndDateBetween(String carModel, Date startDate, Date endDate);

}
