package ru.pelse.myjira.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pelse.myjira.entity.Image;

public interface ImageRepository extends CrudRepository<Image, Integer> {
}
