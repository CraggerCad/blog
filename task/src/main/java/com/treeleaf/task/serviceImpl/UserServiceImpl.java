package com.treeleaf.task.serviceImpl;

import com.treeleaf.task.response.AuthenticationResponse;
import com.treeleaf.task.response.PageResponse;
import com.treeleaf.task.constants.ROLE;
import com.treeleaf.task.exceptionHandler.CustomException;
import com.treeleaf.task.model.Role;
import com.treeleaf.task.model.User;
import com.treeleaf.task.repo.UserRepo;
import com.treeleaf.task.security.util.JwtUtil;
import com.treeleaf.task.service.RoleService;
import com.treeleaf.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

/**
 * Created by User on 2/12/2022.
 */

@Service
public class UserServiceImpl implements UserService {


    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    @Autowired
    UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleService roleService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse authenticate(User user) throws CustomException {
        final Authentication authentication;

        if (user.getUserName() == null || user.getPassword() == null) {
            throw new CustomException("Email/Password not found, error.");
        }

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect email or password");
        } catch (LockedException lockedException) {
            throw new LockedException("Account Locked.");
        } catch (DisabledException disabledException) {
            throw new DisabledException("Account disabled.");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities());
        return new AuthenticationResponse(jwt);
    }

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleService.getRoleByName(ROLE.USER.value);
        user.setRoles(Collections.singletonList(role));
        user.setDeleted(false);
        user = userRepo.save(user);
        user.setPassword(null);
        return user;
    }

    @Override
    public User delete(long id) throws CustomException {
        User user = userRepo.findById(id).orElseThrow(() -> new CustomException("User for corresponding id not found"));
        user.setDeleted(true);
        user.setPassword(null);
        return user;
    }

    @Override
    public User updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepo.save(user);
        user.setPassword(null);
        return user;
    }

    @Override
    public User activate(long id) throws CustomException {
        User user = userRepo.findById(id).orElseThrow(() -> new CustomException("User not found"));
        user.setEnabled(true);
        userRepo.save(user);
        user.setPassword(null);
        return user;
    }

    @Override
    public User deActivate(long id) throws CustomException {
        User user = userRepo.findById(id).orElseThrow(() -> new CustomException("User not found"));
        user.setEnabled(false);
        userRepo.save(user);
        user.setPassword(null);
        return user;
    }

    @Override
    public PageResponse<User> getUsers(int pageNum, int pageSize, boolean status) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<User> usersPage = userRepo.findAllByEnabled(status, pageable);
        List<User> users = usersPage.getContent();
        users.forEach(u -> {
            u.setPassword(null);
        });
        return new PageResponse<>(usersPage.getNumber(), usersPage.getSize(), usersPage.getTotalElements(), usersPage.getTotalPages(), users);
    }

    @Override
    public User getUserByPrincipal(String principal) {
        return userRepo.findByUserNameAndDeletedIsFalse(principal);
    }
}
