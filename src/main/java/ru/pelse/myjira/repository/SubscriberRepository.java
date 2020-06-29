package ru.pelse.myjira.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pelse.myjira.entity.Subscriber;

public interface SubscriberRepository extends CrudRepository<Subscriber, Integer> {
}
