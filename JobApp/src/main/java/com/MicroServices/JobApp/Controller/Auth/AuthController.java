package com.MicroServices.JobApp.Controller.Auth;

import com.MicroServices.JobApp.Constant.HttpStatusCodeEnum;
import com.MicroServices.JobApp.Controller.Company.CompanyController;
import com.MicroServices.JobApp.Dto.Auth.LoginRequest;
import com.MicroServices.JobApp.Dto.Auth.UserRegisterRequest;
import com.MicroServices.JobApp.Helper.ErrorResponse;
import com.MicroServices.JobApp.Helper.SuccessResponse;
import com.MicroServices.JobApp.Services.Impl.User.UserServices;
import com.MicroServices.JobApp.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserServices userServices;

    /**
     * Registers a new user into the system.
     *
     * @param userRegisterRequest The registration details (e.g., username, email, password).
     * @return ResponseEntity indicating success or failure.
     */
    @PostMapping("/register")
    public ResponseEntity<Object> registeruser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        log.info("UserRegister is calling and RegisterRequest is :-{}", userRegisterRequest.toString());
        log.debug("User Services layer to register the user ....");
        userServices.registerUser(userRegisterRequest);

        SuccessResponse<String> successResponse = new SuccessResponse<>(
                HttpStatusCodeEnum.FOUND,
                "User is register..",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(successResponse);
    }

    /**
     * Authenticates a user and generates a JWT token on success.
     *
     * @param loginRequest The login credentials (username and password).
     * @return ResponseEntity containing the JWT token if authentication is successful,
     * or an error response if authentication fails.
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            String token = jwtTokenProvider.generateToken(authentication);

            SuccessResponse<String> successResponse = new SuccessResponse<>(
                    HttpStatusCodeEnum.OK,
                    "Login successful",
                    token,
                    LocalDateTime.now()
            );

            return ResponseEntity.ok(successResponse);
        } catch (AuthenticationException ex) {
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatusCodeEnum.UNAUTHORIZED,
                    "Invalid username or password",
                    LocalDateTime.now(),
                    ex.getLocalizedMessage(),
                    ""
            );

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
