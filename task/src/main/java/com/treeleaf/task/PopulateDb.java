package com.treeleaf.task;

import com.treeleaf.task.model.Role;
import com.treeleaf.task.model.User;
import com.treeleaf.task.repo.RoleRepo;
import com.treeleaf.task.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.PrimitiveIterator;

/**
 * Created by User on 2/12/2022.
 */

@Component
public class PopulateDb {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;



//    @PostConstruct
    public void addRolesAndAdmin(){


        Role role = new Role();
        Role role1 = new Role();

        role.setRole("USER");
        role1.setRole("ADMIN");

        roleRepo.save(role);
        roleRepo.save(role1);

        User user = new User();
        user.setName("Admin");
        user.setUserName("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setEnabled(true);
        user.setDeleted(false);
        List<Role> roles = roleRepo.findAll();
        user.setRoles(roles);

        userRepo.save(user);
    }

}

