package com.MicroServices.JobApp.Services.Impl.User;

import com.MicroServices.JobApp.Dto.Auth.UserRegisterRequest;

public interface UserServices {
    void registerUser(UserRegisterRequest userRegisterRequest);
}
