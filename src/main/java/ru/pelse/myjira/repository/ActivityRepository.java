package ru.pelse.myjira.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pelse.myjira.entity.Activity;

public interface ActivityRepository extends CrudRepository<Activity, Integer> {
}
