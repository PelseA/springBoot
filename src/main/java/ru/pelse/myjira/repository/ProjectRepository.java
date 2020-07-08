package ru.pelse.myjira.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pelse.myjira.entity.Project;
import ru.pelse.myjira.entity.Task;
import ru.pelse.myjira.entity.User;

public interface ProjectRepository extends CrudRepository<Project, Integer> {

    Iterable<Project> findByUser(User user);

    Project findByTasks(Task task);
}

