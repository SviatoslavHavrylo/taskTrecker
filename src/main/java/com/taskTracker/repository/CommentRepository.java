package com.taskTracker.repository;

import com.taskTracker.domain.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAllByTask_IdAndAndDeleted(Long taskId, boolean deleted);
}
