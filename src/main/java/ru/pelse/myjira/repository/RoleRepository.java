package ru.pelse.myjira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pelse.myjira.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRole(String role);
}
