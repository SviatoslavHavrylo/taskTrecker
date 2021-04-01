package com.taskTracker.service;

import com.taskTracker.common.dto.CommentDto;
import com.taskTracker.domain.Comment;
import com.taskTracker.exception.LogicFailException;
import com.taskTracker.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TaskCommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public TaskCommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = new Comment();

        return new CommentDto(comment);
    }

    public CommentDto deleteComment(Long commentId) {
        Comment comment = findCommentById(commentId);

        return new CommentDto(comment);
    }

    private Comment findCommentById(Long commentId) {
        if (Objects.isNull(commentId)) new LogicFailException("comment id can't be NULL");

        return commentRepository.findById(commentId)
                .orElseThrow(
                        () -> new LogicFailException(String.format("no task with id - %s", commentId))
                );
    }
}
