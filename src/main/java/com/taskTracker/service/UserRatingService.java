package com.taskTracker.service;

import com.taskTracker.model.UserRating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRatingService {

    public List<UserRating> getUserRating(List<Long> userIds) {
        WebClient webClient = WebClient.create();

        return webClient.get()
                .url("http://ratings-service/users/")
                .retrieve()
                .bodyToMono(UserRating.class)
                .block();
    }
}
