package com.treeleaf.task.repo;

import com.treeleaf.task.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by User on 2/12/2022.
 */
public interface BlogRepo extends JpaRepository<Blog, Long>{

    @Query(nativeQuery = true, value = "select * from blog where created_by_id = (select id from user where user_name like ?1)")
    Page<Blog> getBlogs(String userName, Pageable pageable);


}
