package com.taskTracker.common;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TaskStatusEnum {

    OPEN(1, "Open"),
    IN_PROGRESS(2, "In progress"),
    COMPLETE(3, "Complete");

    private Integer id;

    private String status;

    TaskStatusEnum(final Integer id, final String status) {
        this.id = id;
        this.status = status;
    }

    public static TaskStatusEnum getById(Integer id) {
        return Arrays.stream(TaskStatusEnum.values())
                .filter(assetTestStatusName -> assetTestStatusName.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
