package com.carrental.bookingservice;

import com.carrental.bookingservice.modules.inventory.controller.req.QueryAvailableCarsReq;
import com.carrental.bookingservice.modules.inventory.entity.Inventory;
import com.carrental.bookingservice.modules.inventory.service.InventoryService;
import com.carrental.bookingservice.modules.inventory.task.UpdateInventoryTask;
import com.carrental.bookingservice.modules.order.controller.req.CreateOrderReq;
import com.carrental.bookingservice.modules.order.entity.Order;
import com.carrental.bookingservice.modules.order.service.OrderService;
import com.carrental.bookingservice.modules.user.controller.req.SignUpReq;
import com.carrental.bookingservice.modules.user.entity.User;
import com.carrental.bookingservice.modules.user.service.UserService;
import com.carrental.bookingservice.util.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest(classes = BookingServiceApplication.class)
class BookingServiceApplicationTests {
	private static final String BMW_650 = "BMW 650";
	private static final String TOYOTA_CAMRY = "Toyota Camry";

	@Autowired
	private UserService userService;

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private UpdateInventoryTask updateInventoryTask;

	@Test
	void contextLoads() {
	}

	@Test
	void testUser() {
		SignUpReq req = new SignUpReq();
		req.setName("leo");
		req.setPassword("123");
		userService.signUp(req);

		List<User> users = userService.getAllUsers();
		assert users.size() == 2;
		assert users.get(1).getName().equals("leo");
	}

	@Test
	void testInventory() throws Exception {
		QueryAvailableCarsReq req = new QueryAvailableCarsReq();
		Date today = TimeUtil.getToday();
		Date tomorrow = TimeUtil.getTomorrow();
		req.setStartDate(today);
		req.setEndDate(tomorrow);
		List<Inventory> inventories = inventoryService.queryAvailableCars(req);
		assert inventories.size() == 4;
		for (Inventory inventory : inventories) {
			assert inventory.getNumInStock() == 2;
		}

		inventoryService.reduceInventory(1, TOYOTA_CAMRY, today, tomorrow);
		inventoryService.reduceInventory(1, BMW_650, today, tomorrow);

		inventories = inventoryService.queryAvailableCars(req);
		assert inventories.size() == 4;
		for (Inventory inventory : inventories) {
			assert inventory.getNumInStock() == 1;
		}

		inventoryService.increaseInventory(1, TOYOTA_CAMRY, today, tomorrow);
		inventoryService.increaseInventory(1, BMW_650, today, tomorrow);

		inventories = inventoryService.queryAvailableCars(req);
		assert inventories.size() == 4;
		for (Inventory inventory : inventories) {
			assert inventory.getNumInStock() == 2;
		}
	}

	@Test
	void testOrder() throws Exception {
		User user = userService.getAllUsers().get(0);
		CreateOrderReq createOrderReq = new CreateOrderReq();
		createOrderReq.setStartDate(TimeUtil.getToday());
		createOrderReq.setEndDate(TimeUtil.getTomorrow());
		createOrderReq.setCarModel(BMW_650);
		// place one order of bmw 650
		orderService.createOrder(user, createOrderReq);

		createOrderReq.setCarModel(TOYOTA_CAMRY);
		// place another order of toyota camry
		orderService.createOrder(user, createOrderReq);

		List<Order> orders = orderService.queryOrders(user);
		assert orders.size() == 2;
		assert orders.get(0).getStatus().equals(Order.Status.NEW.value);
		assert orders.get(1).getStatus().equals(Order.Status.NEW.value);

		QueryAvailableCarsReq req = new QueryAvailableCarsReq();
		req.setStartDate(TimeUtil.getToday());
		req.setEndDate(TimeUtil.getTomorrow());
		List<Inventory> inventories = inventoryService.queryAvailableCars(req);
		assert inventories.size() == 4;
		for (Inventory inventory : inventories) {
			// inventories should be reduced to 1
			assert inventory.getNumInStock() == 1;
		}

		// cancel the bmw order
		orderService.cancelOrder(user, orders.get(0).getId());
		// pay the toyota order
		orderService.payOrder(user, orders.get(1).getId());

		orders = orderService.queryOrders(user);
		assert orders.size() == 2;
		assert orders.get(0).getStatus().equals(Order.Status.CANCELED.value);
		assert orders.get(1).getStatus().equals(Order.Status.PAID.value);

		inventories = inventoryService.queryAvailableCars(req);
		assert inventories.size() == 4;
		for (Inventory inventory : inventories) {
			if (inventory.getCarModel().equals(BMW_650)) {
				assert inventory.getNumInStock() == 2;
			} else {
				assert inventory.getNumInStock() == 1;
			}
		}

		// complete the toyota order
		orderService.completeOrder(user, orders.get(1).getId());
		orders = orderService.queryOrders(user);
		assert orders.size() == 2;
		assert orders.get(1).getStatus().equals(Order.Status.COMPLETE.value);

		inventories = inventoryService.queryAvailableCars(req);
		assert inventories.size() == 4;
		for (Inventory inventory : inventories) {
			assert inventory.getNumInStock() == 2;
		}
	}

	@Test
	void testInventoryTask() throws Exception {
		User user = userService.getAllUsers().get(0);
		CreateOrderReq createOrderReq = new CreateOrderReq();
		createOrderReq.setStartDate(TimeUtil.getToday());
		createOrderReq.setEndDate(TimeUtil.getTomorrow());
		createOrderReq.setCarModel(BMW_650);
		// place one order of bmw 650
		orderService.createOrder(user, createOrderReq);

		createOrderReq.setCarModel(TOYOTA_CAMRY);
		// place another order of toyota camry
		orderService.createOrder(user, createOrderReq);

		QueryAvailableCarsReq req = new QueryAvailableCarsReq();
		req.setStartDate(TimeUtil.getToday());
		req.setEndDate(TimeUtil.getTomorrow());
		List<Inventory> inventories = inventoryService.queryAvailableCars(req);
		assert inventories.size() == 4;
		for (Inventory inventory : inventories) {
			// inventories should be reduced to 1
			assert inventory.getNumInStock() == 1;
		}

		updateInventoryTask.run();

		inventories = inventoryService.queryAvailableCars(req);
		assert inventories.size() == 4;
		for (Inventory inventory : inventories) {
			// inventories should be reduced to 1
			assert inventory.getNumInStock() == 1;
		}
	}
}
