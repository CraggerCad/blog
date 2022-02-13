package com.treeleaf.task.serviceImpl;

import com.treeleaf.task.exceptionHandler.CustomException;
import com.treeleaf.task.model.Blog;
import com.treeleaf.task.model.Comment;
import com.treeleaf.task.model.User;
import com.treeleaf.task.repo.CommentRepo;
import com.treeleaf.task.service.BlogService;
import com.treeleaf.task.service.CommentService;
import com.treeleaf.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Collections;

/**
 * Created by User on 2/12/2022.
 */
@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepo commentRepo;
    private BlogService blogService;
    private UserService userService;

    @Autowired
    CommentServiceImpl(CommentRepo commentRepo, BlogService blogService, UserService userService) {
        this.commentRepo = commentRepo;
        this.blogService = blogService;
        this.userService = userService;
    }


    @Override
    public Comment putComment(Principal principal, Comment comment) throws CustomException {
        Blog blog = blogService.findById(comment.getBlog().getId());
        User user = userService.getUserByPrincipal(principal.getName());
        comment.setUser(user);
        comment = commentRepo.save(comment);

        if (blog.getComments() == null) {
            System.out.println("Adding first comment");
            blog.setComments(Collections.singletonList(comment));
        } else {
            System.out.println("Appending comment");
            blog.getComments().add(comment);
        }

        blogService.saveBlog(blog);

        comment.getUser().setPassword(null);
        comment.getBlog().setComments(null);
        return comment;
    }

    @Override
    public Comment editComment(Comment comment) throws CustomException{
        Comment oldComment = commentRepo.findById(comment.getId()).orElseThrow(()-> new CustomException("Old comment not found."));
        oldComment.setComment(comment.getComment());

        comment = commentRepo.save(oldComment);
        comment.getUser().setPassword(null);
        return comment;
    }
}
