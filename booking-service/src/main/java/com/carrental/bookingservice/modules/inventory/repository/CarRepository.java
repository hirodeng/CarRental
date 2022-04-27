package com.carrental.bookingservice.modules.inventory.repository;

import com.carrental.bookingservice.modules.inventory.entity.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {

}
