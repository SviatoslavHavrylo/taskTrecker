package com.taskTracker.service;

import com.taskTracker.common.dto.CommentDto;
import com.taskTracker.common.dto.TaskDto;
import com.taskTracker.domain.Division;
import com.taskTracker.repository.DivisionRepository;
import com.taskTracker.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final UserRatingService userRatingService;

    private final AttachmentService attachmentService;

    private final TaskRepository taskRepository;

    private final DivisionRepository divisionRepository;

    @Autowired
    public TaskService(UserRatingService userRatingService, AttachmentService attachmentService,
                       TaskRepository taskRepository, DivisionRepository divisionRepository) {
        this.userRatingService = userRatingService;
        this.attachmentService = attachmentService;
        this.taskRepository = taskRepository;
        this.divisionRepository = divisionRepository;
    }

    public List<TaskDto> getTasks(String division) {
        Division division1 = new Division();
        divisionRepository.save(division1);

        return null;
    }

    public TaskDto getTaskInfo(Long taskId) {
        return null;
    }

    public TaskDto createTask(TaskDto taskDto) {
        return null;
    }

    public TaskDto updateTask(TaskDto taskDto) {
        return null;
    }

    public CommentDto createComment(CommentDto commentDto) {
        return null;
    }

    public CommentDto deleteComment(Long commentId) {
        return null;
    }
}
