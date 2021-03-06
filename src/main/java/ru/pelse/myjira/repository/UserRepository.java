package ru.pelse.myjira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import ru.pelse.myjira.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByActivationCode(String code);

    User findByUsername(String username);
}
