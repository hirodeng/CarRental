package com.carrental.bookingservice.config;

import com.carrental.bookingservice.modules.inventory.task.UpdateInventoryTask;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitInventory implements InitializingBean {
    @Autowired
    private UpdateInventoryTask updateInventoryTask;

    @Override
    public void afterPropertiesSet() throws Exception {
        updateInventoryTask.run();
    }
}
