package ru.pelse.myjira.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pelse.myjira.entity.Activity;
import ru.pelse.myjira.entity.Task;

public interface ActivityRepository extends CrudRepository<Activity, Integer> {
    Iterable<Activity> findByTask(Task task);
}
