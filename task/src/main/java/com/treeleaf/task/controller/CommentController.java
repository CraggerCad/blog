package com.treeleaf.task.controller;

import com.treeleaf.task.exceptionHandler.CustomException;
import com.treeleaf.task.model.Comment;
import com.treeleaf.task.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by User on 2/13/2022.
 */

@RestController
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;

    CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/putComment")
    public ResponseEntity<?> addComment(Principal principal, @RequestBody Comment comment) throws CustomException{
        return new ResponseEntity<Object>(commentService.putComment(principal, comment), HttpStatus.OK  );
    }

    @PutMapping("/editComment")
    public ResponseEntity<?> editComment(@RequestBody Comment comment) throws CustomException{
        return new ResponseEntity<Object>(commentService.editComment(comment), HttpStatus.OK);
    }

}
