package com.treeleaf.task.repo;

import com.treeleaf.task.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by User on 2/12/2022.
 */
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
