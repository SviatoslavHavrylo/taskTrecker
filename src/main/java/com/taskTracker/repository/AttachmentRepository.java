package com.taskTracker.repository;

import com.taskTracker.domain.Attachment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

    List<Attachment> findAllByTask_IdAndAndDeleted(Long taskId, boolean deleted);
}
