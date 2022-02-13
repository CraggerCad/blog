package com.treeleaf.task.repo;

import com.treeleaf.task.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by User on 2/12/2022.
 */
public interface CommentRepo extends JpaRepository<Comment, Long> {

}
