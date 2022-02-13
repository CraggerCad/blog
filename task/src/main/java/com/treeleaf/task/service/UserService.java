package com.treeleaf.task.service;

import com.treeleaf.task.response.AuthenticationResponse;
import com.treeleaf.task.response.PageResponse;
import com.treeleaf.task.exceptionHandler.CustomException;
import com.treeleaf.task.model.User;

/**
 * Created by User on 2/12/2022.
 */
public interface UserService {

    AuthenticationResponse authenticate(User user) throws CustomException;

    User register(User user);

    User delete(long id) throws CustomException;

    User updateUser(User user);

    User activate(long id) throws CustomException;

    User deActivate(long id) throws CustomException;

    PageResponse<User> getUsers(int pageNum, int pageSize, boolean status);

    User getUserByPrincipal(String principal);

}
