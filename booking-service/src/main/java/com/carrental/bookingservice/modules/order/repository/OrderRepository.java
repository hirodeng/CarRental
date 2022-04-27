package com.carrental.bookingservice.modules.order.repository;

import com.carrental.bookingservice.modules.order.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUserId(Long id);

    @Query(value = "select count(*) from t_order where start_date <= ?1 and end_date >= ?1 and car_model = ?2 and status >= 100 and status <= 120",
            nativeQuery = true)
    int countOngoingOrders(Date date, String carModel);

}
