package ru.pelse.myjira.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pelse.myjira.entity.Project;

public interface ProjectRepository extends CrudRepository<Project, Integer> {
}
