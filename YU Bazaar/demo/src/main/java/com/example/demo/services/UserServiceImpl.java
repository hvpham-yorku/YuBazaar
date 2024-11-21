package com.example.demo.services;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean register(User User) {

        try {
            userRepository.save(User);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
