package com.carrental.bookingservice.modules.user.service;

import com.carrental.bookingservice.modules.user.controller.req.SignInReq;
import com.carrental.bookingservice.modules.user.controller.req.SignUpReq;
import com.carrental.bookingservice.modules.user.entity.User;
import com.carrental.bookingservice.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signUp(SignUpReq req) {
        User user = new User();
        user.setName(req.getName());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(user);
    }

    public boolean signIn(SignInReq req) {
        User userInDB = userRepository.findByName(req.getName());
        if (userInDB != null && userInDB.getPassword().equals(passwordEncoder.encode(req.getPassword()))) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            users.add(user);
        }
        return users;
    }
}
