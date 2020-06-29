package ru.pelse.myjira.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pelse.myjira.entity.State;

public interface StateRepository extends CrudRepository<State, Integer> {
}
