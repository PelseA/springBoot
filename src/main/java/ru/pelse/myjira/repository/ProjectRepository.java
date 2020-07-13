package ru.pelse.myjira.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.pelse.myjira.entity.Activity;
import ru.pelse.myjira.entity.Project;
import ru.pelse.myjira.entity.Task;
import ru.pelse.myjira.entity.User;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Integer> {

    Iterable<Project> findByUser(User user);

    Project findByTasks(Task task);
}

