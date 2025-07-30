package com.MicroServices.JobApp.Services.Impl.User;

import com.MicroServices.JobApp.Constant.RoleType;
import com.MicroServices.JobApp.Dto.Auth.UserRegisterRequest;
import com.MicroServices.JobApp.Entity.Role;
import com.MicroServices.JobApp.Entity.User;
import com.MicroServices.JobApp.Repository.RoleRepository;
import com.MicroServices.JobApp.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void registerUser(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setUsername(userRegisterRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterRequest.getPassword()));
        user.setEmail(userRegisterRequest.getEmail());
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        RoleType roleType;
        try {
            roleType = RoleType.valueOf(userRegisterRequest.getRole().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid role provided");
        }

        Role role = roleRepository.findByName(roleType).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Set.of(role));
        userRepository.save(user);
    }
}
