package com.treeleaf.task.service;

import com.treeleaf.task.exceptionHandler.CustomException;
import com.treeleaf.task.model.Blog;
import com.treeleaf.task.response.PageResponse;
import org.springframework.data.domain.Page;

import java.security.Principal;

/**
 * Created by User on 2/12/2022.
 */
public interface BlogService {

    Blog addBlog(Principal principal, Blog blog);

    Blog updateBlog(Blog blog) throws CustomException;

    Blog deleteBlog(long id) throws CustomException;

    PageResponse<Blog> getBlogs(Principal principal, int pageNum, int pageSize);

    Blog findById(long id) throws CustomException;

    Blog saveBlog(Blog blog);
}
