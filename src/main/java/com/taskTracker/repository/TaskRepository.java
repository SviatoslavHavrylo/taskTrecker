package com.taskTracker.repository;

import com.taskTracker.domain.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByDeletedIs(boolean deleted);

    @Query(value = "SELECT task.* FROM task LEFT JOIN user ON task.assignee_id=user.id " +
            " WHERE user.division_id = :divisionId AND deleted = false",
            nativeQuery = true)
    List<Task> findAllByNotDeletedAndByDivisionId(Long divisionId);
}
