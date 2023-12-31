package com.beetech.api_intern.features.user;

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
     * @param changePasswordDto the change password dto
     * @return the response entity
     */
    @PostMapping("change-password")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordDto) {
        Optional<User> optionalUser = UserUtils.getAuthenticatedUser();
        assert optionalUser.isPresent();
        userService.changePassword(optionalUser.get().getId(), changePasswordDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Request password response entity.
     *
     * @param requestPasswordDto the request password dto
     * @return the response entity
     */
    @PostMapping("request-password")
    public ResponseEntity<Object> requestPassword(@Valid @RequestBody RequestPasswordDto requestPasswordDto) {
        String token = userService.requestPassword(requestPasswordDto.getEmail());
        mailService.sendEmail(requestPasswordDto.getEmail(), "Request password", "Token: " + token);
        return ResponseEntity.ok().build();
    }

    /**
     * Reset password response entity.
     *
     * @param resetPasswordDto the reset password dto
     * @return the response entity
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("reset-password")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordDto) {
        // find User by token
        User user = userService.findUserFromToken(resetPasswordDto.getToken());
        // reset user password
        String newPassword = userService.resetPassword(user);

        // block all refresh token of user
        authService.blockAllRefreshToken(user.getId());

        mailService.sendEmail(user.getLoginId(), "Reset password", "New password: " + newPassword);
        return ResponseEntity.ok().build();
    }

    @PostMapping("users")
    public ResponseEntity<List<UserResponse>> findUsers(@Valid @RequestBody ListUserRequest dto) {
        List<UserResponse> users = userService.findUser(dto).stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();
        return ResponseEntity.ok(users);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("delete-user")
    public ResponseEntity<Object> deleteUser() {
        User user = UserUtils.getUser();
        String email = user.getLoginId();
        userService.deleteUser(user);
        authService.blockAllRefreshToken(user.getId());
        mailService.sendEmail(email, "Delete user successfully!", "Deleted");
        return ResponseEntity.ok().build();
    }

    @PostMapping("lock-user/{id}")
    public ResponseEntity<Object> lockUser(@PathVariable Long id) {
        userService.lockUser(id);
        return ResponseEntity.ok().build();
    }
}

