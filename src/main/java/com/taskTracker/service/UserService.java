package com.taskTracker.service;

import com.taskTracker.domain.User;
import com.taskTracker.exception.LogicFailException;
import com.taskTracker.repository.DivisionRepository;
import com.taskTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final DivisionRepository divisionRepository;

    @Autowired
    public UserService(UserRepository userRepository, DivisionRepository divisionRepository) {
        this.userRepository = userRepository;
        this.divisionRepository = divisionRepository;
    }

    public User findUserById(Long userId) {
        if (Objects.isNull(userId)) new LogicFailException("user id can't be NULL");

        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new LogicFailException(String.format("no user with id - %s", userId))
                );
    }
}
