package com.bacpham.identity_service.controller;

import com.bacpham.identity_service.dto.request.ApiResponse;
import com.bacpham.identity_service.dto.request.UserCreationRequest;
import com.bacpham.identity_service.dto.request.UserUpdateRequest;
import com.bacpham.identity_service.dto.response.UserResponse;
import com.bacpham.identity_service.entity.User;
import com.bacpham.identity_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Validated UserCreationRequest request) {
      ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.createUser(request));
        return response;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable String userId) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.getUser(userId));
        return response;
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody @Validated UserUpdateRequest request) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        userService.updateUser(userId, request);
        response.setMessage("User updated successfully");
        return response;
    }

    @DeleteMapping("/{userId}")
    ApiResponse<Void> deleteUser(@PathVariable String userId) {
        ApiResponse<Void> response = new ApiResponse<>();
        userService.deleteUser(userId);
        response.setMessage("User deleted successfully");
        return response;
    }

}
