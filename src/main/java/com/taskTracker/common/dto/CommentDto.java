package com.taskTracker.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.taskTracker.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import static com.taskTracker.service.util.Constants.DATE_TIME_FORMAT;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto implements Serializable {

    private static final long serialVersionUID = -5259204866794981163L;

    private Long commentId;

    private String comment;

    private Long userId;

    private Long taskId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private Date lastModified;

    private boolean deleted;

    public CommentDto(Comment comment) {
        this.commentId = comment.getId();
        this.comment = comment.getComment();
        this.userId = comment.getUser().getId();
        this.taskId = comment.getTask().getId();
        this.lastModified = comment.getLastModified();
        this.deleted = comment.isDeleted();
    }
}
