package com.beetech.api_intern.features.user;

import com.beetech.api_intern.common.responses.CommonResponseBody;
import com.beetech.api_intern.common.utils.UserUtils;
import com.beetech.api_intern.features.email.MailService;
import com.beetech.api_intern.features.user.dto.*;
import com.beetech.api_intern.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final MailService mailService;
    private final ModelMapper modelMapper;

    /**
     * Change password response entity.
     *
     * @param changePasswordRequest the change password request
     * @return the response entity
     */
    @PostMapping("change-password")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        Optional<User> optionalUser = UserUtils.getAuthenticatedUser();
        assert optionalUser.isPresent();
        userService.changePassword(optionalUser.get().getId(), changePasswordRequest);
        return ResponseEntity.ok(new CommonResponseBody<>());
    }

    /**
     * Request password response entity.
     *
     * @param requestPasswordRequest the request password request
     * @return the response entity
     */
    @PostMapping("request-password")
    public ResponseEntity<Object> requestPassword(@Valid @RequestBody RequestPasswordRequest requestPasswordRequest) {
        String token = userService.requestPassword(requestPasswordRequest.getEmail());
        mailService.sendEmail(requestPasswordRequest.getEmail(), "Request password", "Token: " + token);
        return ResponseEntity.ok(new CommonResponseBody<>());
    }

    /**
     * Reset password response entity.
     *
     * @param resetPasswordRequest the reset password request
     * @return the response entity
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("reset-password")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        // find User by token
        User user = userService.findUserFromToken(resetPasswordRequest.getToken());
        // reset user password
        String newPassword = userService.resetPassword(user);

        // block all refresh token of user
        authService.blockAllRefreshToken(user.getId());

        mailService.sendEmail(user.getLoginId(), "Reset password", "New password: " + newPassword);
        return ResponseEntity.ok(new CommonResponseBody<>());
    }

    /**
     * Find users response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("users")
    public ResponseEntity<List<UserResponse>> findUsers(@Valid @RequestBody ListUserRequest request) {
        List<UserResponse> users = userService.findUser(request).stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();
        return ResponseEntity.ok(users);
    }

    /**
     * Delete user response entity.
     *
     * @return the response entity
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("delete-user")
    public ResponseEntity<Object> deleteUser() {
        User user = UserUtils.getUser();
        String email = user.getLoginId();
        userService.deleteUser(user);
        authService.blockAllRefreshToken(user.getId());
        mailService.sendEmail(email, "Delete user successfully!", "Deleted");
        return ResponseEntity.ok(new CommonResponseBody<>());
    }

    /**
     * Lock user response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @PostMapping("lock-user/{id}")
    public ResponseEntity<Object> lockUser(@PathVariable Long id) {
        userService.lockUser(id);
        return ResponseEntity.ok(new CommonResponseBody<>());
    }
}

