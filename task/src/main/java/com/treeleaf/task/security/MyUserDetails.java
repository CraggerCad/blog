package com.treeleaf.task.security;

import com.treeleaf.task.model.Role;
import com.treeleaf.task.repo.UserRepo;
import com.treeleaf.task.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2/12/2022.
 */
@Service
public class MyUserDetails implements UserDetailsService {


    private UserRepo userRepo;

    @Autowired
    MyUserDetails(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        com.treeleaf.task.model.User user = userRepo.findByUserNameAndDeletedIsFalse(userName);
        if (user == null){
            throw new UsernameNotFoundException("Bad Credentials.");
        }
        return new User(user.getUserName(), user.getPassword(), user.isEnabled(), !user.isDeleted(), !user.isDeleted(), user.isEnabled(), getAuthorities(user));
    }

    private List<GrantedAuthority> getAuthorities(com.treeleaf.task.model.User user){
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }
}
