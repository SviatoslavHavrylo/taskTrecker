package com.taskTracker.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TaskDetailsDto implements Serializable {

    private static final long serialVersionUID = 3161236086015520879L;

    private Long taskId;

    private List<CommentDto> commentDtos;

    private List<AttachmentDto> attachmentDtos;

    public TaskDetailsDto(Long taskId, List<CommentDto> commentDtos, List<AttachmentDto> attachmentDtos) {
        this.taskId = taskId;
        this.commentDtos = commentDtos;
        this.attachmentDtos = attachmentDtos;
    }
}
