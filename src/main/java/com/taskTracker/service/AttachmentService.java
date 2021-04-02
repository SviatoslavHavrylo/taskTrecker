package com.taskTracker.service;

import com.taskTracker.common.dto.AttachmentDto;
import com.taskTracker.domain.Attachment;
import com.taskTracker.exception.LogicFailException;
import com.taskTracker.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    private final Path fileStorageLocation;

    @Value("${attachedfiles.dir}")
    private String attachmentsDirectory;

    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.fileStorageLocation = Paths.get(attachmentsDirectory).toAbsolutePath().normalize();
    }

    public Resource loadFileAsResource(Long attachmentId) throws Exception {
        Attachment attachment = findAttachmentById(attachmentId);

        try {
            Path filePath = this.fileStorageLocation.resolve(attachment.getFileName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found");
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found");
        }
    }

    public Attachment findAttachmentById(Long attachmentId) {
        if (Objects.isNull(attachmentId)) new LogicFailException("attachment id can't be NULL");

        return attachmentRepository.findById(attachmentId)
                .orElseThrow(
                        () -> new LogicFailException(String.format("no attachment with id - %s", attachmentId))
                );
    }

    public List<AttachmentDto> getAllArrachmentsByTaskId(Long taskId) {
        List<Attachment> attachments = attachmentRepository.findAllByTask_IdAndAndDeleted(taskId, false);

        return attachments.stream()
                .map(AttachmentDto::new)
                .collect(Collectors.toList());
    }
}
