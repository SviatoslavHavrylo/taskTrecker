package com.taskTracker.common.dto;

import com.taskTracker.domain.Attachment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AttachmentDto implements Serializable {

    private static final long serialVersionUID = -2052553242774158250L;

    private Long attachmentId;

    private String fileName;

    private String fileType;

    public AttachmentDto(Attachment attachment) {
        this.attachmentId = attachment.getId();
        this.fileName = attachment.getFileName();
        this.fileType = attachment.getDocumentType();
    }
}
