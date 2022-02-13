package com.treeleaf.task.controller;

import com.treeleaf.task.exceptionHandler.CustomException;
import com.treeleaf.task.model.Blog;
import com.treeleaf.task.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by User on 2/12/2022.
 */


@RestController
@RequestMapping("/blog")
public class BlogController {

    private BlogService blogService;

    BlogController(BlogService blogService){
        this.blogService = blogService;
    }


    @PostMapping("/addBlog")
    public ResponseEntity<?> addBlog(Principal principal, @RequestBody Blog blog){
        return new ResponseEntity<Object>(blogService.addBlog(principal, blog), HttpStatus.OK);
    }

    @PutMapping("/updateBlog")
    public  ResponseEntity<?> updateBlog(@RequestBody Blog blog) throws CustomException{
        return new ResponseEntity<Object>(blogService.updateBlog(blog), HttpStatus.OK);
    }

    @DeleteMapping("/deleteBlog/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable long id) throws CustomException{
        return new ResponseEntity<Object>(blogService.deleteBlog(id), HttpStatus.OK);
    }

    @GetMapping("/getBlogs")
    public ResponseEntity<?> getBlogs(Principal principal, @RequestParam int pageNum, @RequestParam int pageSize){
        return new ResponseEntity<Object>(blogService.getBlogs(principal, pageNum, pageSize), HttpStatus.OK);
    }

}
