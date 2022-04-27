package com.carrental.bookingservice.modules.order.service;

import com.carrental.bookingservice.modules.inventory.controller.req.QueryAvailableCarsReq;
import com.carrental.bookingservice.modules.inventory.entity.Inventory;
import com.carrental.bookingservice.modules.inventory.service.InventoryService;
import com.carrental.bookingservice.modules.order.controller.req.CreateOrderReq;
import com.carrental.bookingservice.modules.order.entity.Order;
import com.carrental.bookingservice.modules.order.repository.OrderRepository;
import com.carrental.bookingservice.modules.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryService inventoryService;

    public List<Order> queryOrders(User user) {
        return orderRepository.findByUserId(user.getId());
//        ArrayList<Order> orders = new ArrayList<>();
//        for (Order order : orderRepository.findAll()) {
//            orders.add(order);
//        }
//        return orders;
    }

    @Transactional
    public Order createOrder(User user, CreateOrderReq req) throws Exception {
        Integer amount = 0;
        QueryAvailableCarsReq queryAvailableCarsReq = new QueryAvailableCarsReq();
        queryAvailableCarsReq.setStartDate(req.getStartDate());
        queryAvailableCarsReq.setEndDate(req.getEndDate());
        queryAvailableCarsReq.setCarModel(req.getCarModel());
        List<Inventory> inventories = inventoryService.queryAvailableCars(queryAvailableCarsReq);
        for (Inventory inventory : inventories) {
            amount += inventory.getPrice();
        }
        inventoryService.reduceInventory(1, req.getCarModel(), req.getStartDate(), req.getEndDate());

        Order order = new Order();
        order.setCarModel(req.getCarModel());
        order.setUserId(user.getId());
        order.setStartDate(req.getStartDate());
        order.setEndDate(req.getEndDate());
        order.setStatus(Order.Status.NEW.value);
        order.setAmount(amount);
        return orderRepository.save(order);
    }

    public Order payOrder(User user, Long orderId) throws Exception {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (! orderOptional.isPresent()) {
            throw new Exception("Order not found");
        }

        Order order = orderOptional.get();
        if (! order.getUserId().equals(user.getId())) {
            throw new Exception("No authorization");
        }

        if (! order.orderCanPay()) {
            throw new Exception("Can not pay order");
        }

        order.payOrder();
        return orderRepository.save(order);
    }

    @Transactional
    public Order cancelOrder(User user, Long orderId) throws Exception {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (! orderOptional.isPresent()) {
            throw new Exception("Order not found");
        }

        Order order = orderOptional.get();
        if (! order.getUserId().equals(user.getId())) {
            throw new Exception("No authorization");
        }

        if (! order.orderCanCancel()) {
            throw new Exception("Can not cancel order");
        }

        order.cancelOrder();
        orderRepository.save(order);

        inventoryService.increaseInventory(1, order.getCarModel(), order.getStartDate(), order.getEndDate());

        return order;
    }

    @Transactional
    public Order completeOrder(User user, Long orderId) throws Exception {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (! orderOptional.isPresent()) {
            throw new Exception("Order not found");
        }

        Order order = orderOptional.get();
        if (! order.getUserId().equals(user.getId())) {
            throw new Exception("No authorization");
        }

        if (! order.orderCanComplete()) {
            throw new Exception("Can not compete order");
        }

        order.completeOrder();
        orderRepository.save(order);

        inventoryService.increaseInventory(1, order.getCarModel(), order.getStartDate(), order.getEndDate());

        return order;
    }
}
