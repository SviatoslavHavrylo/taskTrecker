package com.taskTracker.service;

import com.taskTracker.common.dto.CommentDto;
import com.taskTracker.domain.Comment;
import com.taskTracker.domain.Task;
import com.taskTracker.domain.User;
import com.taskTracker.exception.LogicFailException;
import com.taskTracker.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskCommentService {

    private final CommentRepository commentRepository;

    private final TaskService taskService;

    private final UserService userService;

    @Autowired
    public TaskCommentService(CommentRepository commentRepository, TaskService taskService, UserService userService) {
        this.commentRepository = commentRepository;
        this.taskService = taskService;
        this.userService = userService;
    }

    public CommentDto createComment(CommentDto commentDto, Long userId) {
        Comment comment = new Comment();
        populateComment(comment, commentDto, userId);
        commentRepository.save(comment);

        return new CommentDto(comment);
    }

    private void populateComment(Comment comment, CommentDto commentDto, Long userId) {
        Task task = taskService.getTaskById(commentDto.getTaskId());
        comment.setTask(task);

        User user = userService.findUserById(userId);
        comment.setUser(user);
        comment.setComment(commentDto.getComment());
    }

    public CommentDto deleteComment(Long commentId) {
        Comment comment = findCommentById(commentId);
        comment.setDeleted(true);
        commentRepository.save(comment);

        return new CommentDto(comment);
    }

    private Comment findCommentById(Long commentId) {
        if (Objects.isNull(commentId)) new LogicFailException("comment id can't be NULL");

        return commentRepository.findById(commentId)
                .orElseThrow(
                        () -> new LogicFailException(String.format("no task with id - %s", commentId))
                );
    }

    public List<CommentDto> getAllCommentsByTaskId(Long taskId) {
        List<Comment> attachments = commentRepository.findAllByTask_IdAndAndDeleted(taskId, false);

        return attachments.stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());
    }
}
