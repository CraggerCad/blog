package com.treeleaf.task.service;

import com.treeleaf.task.exceptionHandler.CustomException;
import com.treeleaf.task.model.Blog;
import com.treeleaf.task.model.Comment;

import java.security.Principal;

/**
 * Created by User on 2/12/2022.
 */
public interface CommentService {

    Comment putComment(Principal principal, Comment comment) throws CustomException;

    Comment editComment(Comment comment) throws CustomException;
}
