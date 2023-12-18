package com.example.taskmanagement.controller;

import com.example.taskmanagement.dao.entity.ForgetfulUser;
import com.example.taskmanagement.dao.entity.UnverifiedUser;
import com.example.taskmanagement.dao.entity.User;
import com.example.taskmanagement.model.request.UserRequestDTO;
import com.example.taskmanagement.service.ForgetfulUserService;
import com.example.taskmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class UserController {

    private final UserService userService;
    private final ForgetfulUserService forgetfulUserService;

    @GetMapping("/by-username-or-email/{usernameOrEmail}")
    @Operation(summary = "This endpoint help us to get user by username or email.")
    public User getByUsernameOrEmail(@PathVariable String usernameOrEmail){
        return userService.getUserByUsernameOrEmail(usernameOrEmail);
    }

    @PostMapping("/register")
    public UnverifiedUser register(@RequestBody UserRequestDTO userRequestDTO){
        return userService.register(userRequestDTO);
    }

    @PutMapping("/verify-user/{id}")
    @Operation(summary = "This endpoint help us to verify user email and add user from unverified_users to users table")
    public void verifyUser(@PathVariable Long id, @RequestParam int otp){
        userService.verifyUser(id, otp);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "This endpoint help us to send otp code to user email")
    public ForgetfulUser forgotPassword(@RequestParam String usernameOrEmail){
        return forgetfulUserService.forgotPassword(usernameOrEmail);
    }

    @PatchMapping("/change-password/{id}")
    @Operation(summary = "This endpoint help us to change password. Take the forgetful user id.")
    public void changePassword(@PathVariable Long id, @RequestParam String newPassword){
        forgetfulUserService.changePassword(id, newPassword);
    }

    @PatchMapping("/check-otp/{id}/{otp}")
    @Operation(summary = "This endpoint help us to check the otp code to send the user.")
    public boolean checkOtp(@PathVariable Long id, @PathVariable int otp){
        return forgetfulUserService.checkOtp(id, otp);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
}
