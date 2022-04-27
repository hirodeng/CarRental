package com.carrental.bookingservice.modules.user.repository;

import com.carrental.bookingservice.modules.user.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);
}
