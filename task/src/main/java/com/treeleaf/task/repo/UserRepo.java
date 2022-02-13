package com.treeleaf.task.repo;

import com.treeleaf.task.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by User on 2/12/2022.
 */
public interface UserRepo extends JpaRepository<User, Long>{

    Page<User> findAllByEnabled(boolean enabled, Pageable pageable);

    User findByUserNameAndDeletedIsFalse(String userName);

}
