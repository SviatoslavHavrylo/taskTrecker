package com.taskTracker.service;

import com.taskTracker.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.List;
import java.util.stream.Collectors;

import static com.taskTracker.service.util.Constants.DEFAULT_USER_RATING;

@Service
public class UserRatingService {

    private final WebClient.Builder webclientBuilder;

    @Autowired
    public UserRatingService(WebClient.Builder webclientBuilder) {
        this.webclientBuilder = webclientBuilder;
    }

    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    public List<UserRating> getUserRating(List<Long> userIds) {
        return webclientBuilder.build().get()
                .url("http://ratings-service/users/")
                .retrieve()
                .bodyToMono(UserRating.class)
                .block();
    }

    public List<UserRating> getFallbackUserRating(List<Long> userIds) {
        return userIds.stream()
                .map(userId -> new UserRating(userId, DEFAULT_USER_RATING))
                .collect(Collectors.toList());
    }
}
