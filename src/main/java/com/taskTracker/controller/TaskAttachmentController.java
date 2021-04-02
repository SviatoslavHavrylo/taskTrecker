package com.taskTracker.controller;

import com.taskTracker.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "/taskTracker/v1/")
public class TaskAttachmentController extends BaseController {

    private final AttachmentService attachmentService;

    @Autowired
    public TaskAttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam("attachmentId") Long attachmentId,
                                                 HttpServletRequest request) {
        Resource resource;
        try {
            resource = attachmentService.loadFileAsResource(attachmentId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);

            return createErrorResponse();
        }

        String contentType = "application/octet-stream";
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        }
        String headerContent = String.format("attachment; filename=\"%s\"", resource.getFilename());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerContent)
                .body(resource);
    }
}
