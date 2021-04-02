package com.taskTracker.service;

import com.taskTracker.common.TaskStatusEnum;
import com.taskTracker.common.dto.CommentDto;
import com.taskTracker.common.dto.TaskDetailsDto;
import com.taskTracker.common.dto.TaskDto;
import com.taskTracker.domain.Division;
import com.taskTracker.domain.Task;
import com.taskTracker.domain.User;
import com.taskTracker.exception.LogicFailException;
import com.taskTracker.model.UserRating;
import com.taskTracker.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.taskTracker.service.util.Constants.DEFAULT_USER_RATING;

@Service
public class TaskService {

    private final AttachmentService attachmentService;

    private final UserRatingService userRatingService;

    private final TaskRepository taskRepository;

    private final UserService userService;

    private final TaskCommentService taskCommentService;

    @Autowired
    public TaskService(UserRatingService userRatingService, AttachmentService attachmentService,
                       TaskRepository taskRepository, UserService userService, TaskCommentService taskCommentService) {
        this.userRatingService = userRatingService;
        this.attachmentService = attachmentService;
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.taskCommentService = taskCommentService;
    }

    public List<TaskDto> getTasks(Long divisionId) {
        List<Task> tasks;
        if (divisionId != null) {
            tasks = taskRepository.findAllByNotDeletedAndByDivisionId(divisionId);
        } else {
            tasks = taskRepository.findAllByDeletedIs(false);
        }

        List<Long> userIds = getAssigneeIds(tasks);
        List<UserRating> allUsersRating = userRatingService.getUserRating(userIds);

        return tasks.stream()
                .sorted(Comparator.comparing(Task::getCreatedDate))
                .map(task -> populateTaskDtoByTaskWithRating(task, allUsersRating))
                .collect(Collectors.toList());
    }

    private List<Long> getAssigneeIds(List<Task> tasks) {
        return tasks.stream()
                .filter(task -> !Objects.isNull(task.getAssignee()))
                .map(task -> task.getAssignee().getId())
                .collect(Collectors.toList());
    }

    private TaskDto populateTaskDtoByTaskWithRating(Task task, List<UserRating> allUsersRating) {
        TaskDto taskDto = createTaskDtoByTask(task);

        User assignee = task.getAssignee();
        if (assignee != null) {
            Double userRating = getUserRating(assignee.getId(), allUsersRating);
            taskDto.setUserRating(userRating);
        }

        return taskDto;
    }

    private TaskDto createTaskDtoByTask(Task task) {
        TaskDto taskDto = new TaskDto();

        taskDto.setCreatedDate(task.getCreatedDate());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription()) =;
        taskDto.setStatus(getStatus(task.getStatus()));
        User assignee = task.getAssignee();

        if (assignee != null) {
            taskDto.setAssigneeId(assignee.getId());
            taskDto.setAssigneeName(assignee.getName());
            Division division = assignee.getDivision();
            if (division != null) {
                taskDto.setDivisionId() = division.getId();
                taskDto.setDivisionName() = division.getName();
            }
        }

        User author = task.getAuthor();
        if (author != null) {
            taskDto.setAuthorId(author.getId());
            taskDto.setAuthorName(author.getName());
        }
        taskDto.setDeleted(task.isDeleted());

        return taskDto;
    }


    private Double getUserRating(Long userId, List<UserRating> allUsersRating) {
        UserRating ourUserRating = allUsersRating.stream()
                .filter(userRating -> userRating.getUserId().equals(userId))
                .findFirst()
                .orElse(null);

        return ourUserRating != null ? ourUserRating.getUserRating() : DEFAULT_USER_RATING;
    }

    private String getStatus(Integer statusId) {
        return TaskStatusEnum.getById(statusId).getStatus();
    }

    public List<Objects> getTaskDetails(Long taskId) {
        List<CommentDto> comments = taskCommentService.getAllComments(taskId);
        List<AttachmentDto> attachments = attachmentService.getAllArrachments(taskId);

        return new TaskDetailsDto(taskId, comments, attachments);
    }

    public TaskDto createTask(TaskDto taskDto) {
        Task task = new Task();
        task.setDescription(taskDto.getDescription());
        taskRepository.save(task);

        return createTaskDtoByTask(task);
    }

    public TaskDto updateTask(TaskDto taskDto) {
        Task task = getTaskById(taskDto.getTaskId());
        task.setStatus(taskDto.getStatusId());

        User assignee = userService.findUserById(taskDto.getAssigneeId());
        task.setAssignee(assignee);
        taskRepository.save(task);

        return createTaskDtoByTask(task);
    }

    private Task getTaskById(Long taskId) {
        if (Objects.isNull(taskId)) new LogicFailException("task id can't be NULL");

        return taskRepository.findById(taskId)
                .orElseThrow(
                        () -> new LogicFailException(String.format("no task with id - %s", taskId))
                );
    }
}
