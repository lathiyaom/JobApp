package com.MicroServices.JobApp.Repository;

import com.MicroServices.JobApp.Constant.RoleType;
import com.MicroServices.JobApp.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleType roleType);
}
