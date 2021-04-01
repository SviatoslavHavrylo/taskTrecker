package com.taskTracker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.taskTracker.domain.Division;

@Repository
public interface DivisionRepository extends CrudRepository<Division, Long> {
}
