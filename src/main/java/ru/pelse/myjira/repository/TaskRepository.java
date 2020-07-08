package ru.pelse.myjira.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.pelse.myjira.entity.Task;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer>, JpaSpecificationExecutor<Task> {
    // JPQL
    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId")
    List<Task> findByProjectId(@Param("projectId") int projectId);
}
