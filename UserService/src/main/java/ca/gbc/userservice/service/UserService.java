package ca.gbc.userservice.service;

import ca.gbc.userservice.dto.UserRequest;
import ca.gbc.userservice.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    Optional<UserResponse> getUserById(Long id);
    List<UserResponse> getAllUsers();
    List<UserResponse> getUsersByRole(String role);
    UserResponse updateUser(Long id, UserRequest userRequest);
    void deleteUser(Long id);
}
