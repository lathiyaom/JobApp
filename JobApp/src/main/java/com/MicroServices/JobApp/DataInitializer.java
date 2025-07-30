package com.MicroServices.JobApp;

import com.MicroServices.JobApp.Constant.RoleType;
import com.MicroServices.JobApp.Entity.Role;
import com.MicroServices.JobApp.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        for (RoleType roleType : RoleType.values()) {
            roleRepository.findByName(roleType)
                    .orElseGet(() -> roleRepository.save(new Role(roleType, new HashSet<>())));
        }
    }
}
