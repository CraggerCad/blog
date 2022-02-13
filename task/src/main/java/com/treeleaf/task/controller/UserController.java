package com.treeleaf.task.controller;

import com.treeleaf.task.exceptionHandler.CustomException;
import com.treeleaf.task.model.User;
import com.treeleaf.task.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by User on 2/12/2022.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) throws CustomException {
        return new ResponseEntity<>(userService.authenticate(user), HttpStatus.OK);
    }


    @PostMapping("/registerUser")
    public ResponseEntity<?> register(@RequestBody User user){
        return new ResponseEntity<>(userService.register(user), HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<?> updateUser(User user){
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getUsers(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestParam boolean status){
        return new ResponseEntity<>(userService.getUsers(pageNum, pageSize, status), HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable long id) throws CustomException {
        return new ResponseEntity<Object>(userService.delete(id), HttpStatus.OK);
    }

    @PutMapping("/activateUser/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> activateUser(@PathVariable long id) throws CustomException{
        return new ResponseEntity<>(userService.activate(id), HttpStatus.OK);
    }

    @PutMapping("/deActivateUser/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deActivateUser(@PathVariable long id) throws CustomException{
        return new ResponseEntity<>(userService.deActivate(id), HttpStatus.OK);
    }

}
