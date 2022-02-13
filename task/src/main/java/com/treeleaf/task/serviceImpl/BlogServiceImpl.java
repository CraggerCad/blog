package com.treeleaf.task.serviceImpl;

import com.treeleaf.task.exceptionHandler.CustomException;
import com.treeleaf.task.model.Blog;
import com.treeleaf.task.model.User;
import com.treeleaf.task.repo.BlogRepo;
import com.treeleaf.task.response.PageResponse;
import com.treeleaf.task.service.BlogService;
import com.treeleaf.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * Created by User on 2/12/2022.
 */
@Service
public class BlogServiceImpl implements BlogService{

    private BlogRepo blogRepo;
    private UserService userService;

    @Autowired
    BlogServiceImpl(BlogRepo blogRepo, UserService userService){
        this.blogRepo = blogRepo;
        this.userService = userService;
    }

    @Override
    public Blog addBlog(Principal principal, Blog blog) {
        User user = userService.getUserByPrincipal(principal.getName());
        blog = blogRepo.save(blog);
        blog.setCreatedBy(user);
        blog = blogRepo.save(blog);
        blog.getCreatedBy().setPassword(null);

        return blog;
    }

    @Override
    public Blog updateBlog(Blog blog) throws CustomException{
        Blog blog1 = blogRepo.findById(blog.getId()).orElseThrow(()-> new CustomException("Corresponding blog not found."));
        blog1.setBlog(blog.getBlog());
        blog1 = blogRepo.save(blog1);
        blog1.getCreatedBy().setPassword(null);
        return blog1;
    }

    @Override
    public Blog deleteBlog(long id) throws CustomException {
        Blog blog = blogRepo.findById(id).orElseThrow(()->new CustomException("Corresponding blog not found."));
        blog.setDeleted(true);
        blogRepo.save(blog);
        return null;
    }

    @Override
    public PageResponse<Blog> getBlogs(Principal principal, int pageNum, int pageSize) {
        Page<Blog> blogsPage =  blogRepo.findAll(PageRequest.of(pageNum, pageSize));
        List<Blog> blogs = blogsPage.getContent();
        blogs.forEach(b->{
            b.getCreatedBy().setPassword(null);
            if (b.getComments() != null){
                b.getComments().forEach(c->{
                    c.getUser().setPassword(null);
                    c.setBlog(null);
                });
            }
        });
        return new PageResponse<>(blogsPage.getNumber(), blogsPage.getSize(), blogsPage.getTotalElements(),blogsPage.getTotalPages(), blogs);
    }

    @Override
    public Blog findById(long id) throws CustomException {
        return blogRepo.findById(id).orElseThrow(()-> new CustomException("Blog not found."));
    }

    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepo.save(blog);
    }
}
