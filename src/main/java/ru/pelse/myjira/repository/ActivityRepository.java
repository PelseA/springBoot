package ru.pelse.myjira.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.pelse.myjira.entity.Activity;
import ru.pelse.myjira.entity.Project;
import ru.pelse.myjira.entity.Task;

import java.util.List;

public interface ActivityRepository extends CrudRepository<Activity, Integer> {
    Iterable<Activity> findByTask(Task task);

    // JPQL
//    @Query("SELECT a FROM Activity a INNER JOIN project p ON p.id = a.task.project.id WHERE p.id = projectId")
//    List<Activity> findByProjectId(@Param("projectId") int projectId);

    // JPQL
    //get all activities in project
    @Query("SELECT a FROM Activity a JOIN a.task t JOIN t.project p WHERE p.id = :projectId")
    List<Activity> findByProjectId(@Param("projectId") int projectId);
}
