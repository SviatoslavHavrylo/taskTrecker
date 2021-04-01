package com.taskTracker.service;

import org.springframework.stereotype.Service;

@Service
public class UserRatingService {

    public String getUserRating(Long userId) {
        WebClient webClient = WebClient.create();

        return webClient.get()
                .uri("http://ratings-service/users/" + userId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
