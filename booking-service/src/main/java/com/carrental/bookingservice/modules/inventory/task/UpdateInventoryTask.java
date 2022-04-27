package com.carrental.bookingservice.modules.inventory.task;

import com.carrental.bookingservice.modules.inventory.entity.Car;
import com.carrental.bookingservice.modules.inventory.entity.Inventory;
import com.carrental.bookingservice.modules.inventory.repository.CarRepository;
import com.carrental.bookingservice.modules.inventory.repository.InventoryRepository;
import com.carrental.bookingservice.modules.order.repository.OrderRepository;
import com.carrental.bookingservice.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class UpdateInventoryTask {
    public static final int DAYS_AHEAD = 30;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void run() {
        Date now = TimeUtil.getToday();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DATE, DAYS_AHEAD);
        Date endDate = c.getTime();


        for (Car car : carRepository.findAll()) {
            List<Inventory> inventoryList =
                    inventoryRepository.findByCarModelAndDateBetween(car.getCarModel(), now, endDate);

            Date lastDate = now;
            for (Inventory inventory : inventoryList) {
                inventory.setPrice(car.getPrice());
                inventory.setNumInStock(car.getNumber());
                lastDate = inventory.getDate();
            }

            c.setTime(lastDate);
            while (c.getTime().before(endDate)) {
                c.add(Calendar.DATE, 1);
                Inventory inventory = new Inventory();
                inventory.setCarModel(car.getCarModel());
                inventory.setPrice(car.getPrice());
                inventory.setDate(c.getTime());
                inventory.setNumInStock(car.getNumber());
                inventoryList.add(inventory);
            }

            for (Inventory inventory : inventoryList) {
                int count = orderRepository.countOrdersAt(inventory.getDate(), inventory.getCarModel());
                inventory.setNumInStock(inventory.getNumInStock() - count);
            }

            inventoryRepository.saveAll(inventoryList);
        }
    }

}
