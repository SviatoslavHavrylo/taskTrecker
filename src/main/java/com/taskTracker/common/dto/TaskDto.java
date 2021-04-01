package com.taskTracker.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import static com.taskTracker.service.util.Constants.DATE_TIME_FORMAT;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto implements Serializable {

    private static final long serialVersionUID = 3161236086015520879L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private Date createdDate;

    private String title;

    private String description;

    private String status;

    private Long divisionId;

    private String divisionName;

    private Long userId;

    private String userName;

    private Double userRating;

    private Long authorId;

    private String authorName;
}
