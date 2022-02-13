package com.treeleaf.task.serviceImpl;

import com.treeleaf.task.model.Role;
import com.treeleaf.task.repo.RoleRepo;
import com.treeleaf.task.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * Created by User on 2/12/2022.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepo roleRepo;

    RoleServiceImpl(RoleRepo roleRepo){
        this.roleRepo = roleRepo;
    }


    @Override
    public Role getRoleByName(String roleName) {
        return roleRepo.findByRole(roleName);
    }
}
