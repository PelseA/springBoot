package ru.pelse.myjira.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pelse.myjira.entity.State;

import java.util.Optional;

public interface StateRepository extends CrudRepository<State, Integer> {
    Optional<State> findByValue(String state);
}
