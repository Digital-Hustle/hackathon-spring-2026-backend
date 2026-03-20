package ru.ci_trainee.authms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ci_trainee.authms.dto.request.UserLoginRq;
import ru.ci_trainee.authms.dto.request.UserRegisterRs;
import ru.ci_trainee.authms.dto.response.JwtRs;
import ru.ci_trainee.authms.mapper.UserMapper;
import ru.ci_trainee.authms.service.logic.AuthService;
import ru.ci_trainee.authms.validation.OnCreate;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    @Operation(
            summary = "Login user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad credentials"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    public JwtRs login(@RequestBody @Validated UserLoginRq loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Register user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "New user created"),
                    @ApiResponse(responseCode = "400", description = "Validation failed"),
                    @ApiResponse(responseCode = "400", description = "Passwords mismatch"),
                    @ApiResponse(responseCode = "400", description = "Id should not be specified")
            }
    )
    public UserRegisterRs register(@RequestBody @Validated(OnCreate.class) UserRegisterRs userRegisterRs) {
        var user = userMapper.toEntity(userRegisterRs);
        userRegisterRs = userMapper.toDto(authService.register(user, userRegisterRs.getPasswordConfirmation()));

        return userRegisterRs;
    }

    @Operation(
            summary = "Refresh tokens",
            responses = {
                    @ApiResponse(responseCode = "200", description = "New tokens")
            }
    )
    @PostMapping("/refresh")
    public JwtRs refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
