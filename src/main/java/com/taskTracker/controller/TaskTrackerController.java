package com.taskTracker.controller;

import com.taskTracker.Response;
import com.taskTracker.common.dto.CommentDto;
import com.taskTracker.common.dto.TaskDto;
import com.taskTracker.service.TaskCommentService;
import com.taskTracker.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/taskTracker/v1/")
public class TaskTrackerController extends BaseController {

    private final TaskService taskService;

    private final TaskCommentService taskCommentService;

    @Autowired
    public TaskTrackerController(TaskService taskService, TaskCommentService taskCommentService) {
        this.taskService = taskService;
        this.taskCommentService = taskCommentService;
    }

    @GetMapping("/getTaskList")
    public Response getTasks(@RequestParam(required = false) Long divisionId) {
        return handleResponse("get tasks",
                () -> taskService.getTasks(divisionId));
    }

    @GetMapping("/getTaskDetails")
    public Response getTaskDetails(@RequestParam Long taskId) {
        return handleResponse("task details",
                () -> taskService.getTaskDetails(taskId));
    }

    @PostMapping("/createTask")
    public Response createTask(TaskDto taskDto) {
        return handleResponse("task created",
                () -> taskService.createTask(taskDto));
    }

    @PutMapping("/updateTask")
    public Response updateTask(TaskDto taskDto) {
        return handleResponse("task updated",
                () -> taskService.updateTask(taskDto));
    }

    @PostMapping("/createComment")
    public Response createComment(CommentDto commentDto) {
        return handleResponse("asset test created",
                () -> taskCommentService.createComment(commentDto));
    }

    @DeleteMapping("/deleteComment")
    public Response deleteComment(@RequestParam("commentId") Long commentId) {
        return handleResponse("asset test deleted",
                () -> taskCommentService.deleteComment(commentId));
    }
}
