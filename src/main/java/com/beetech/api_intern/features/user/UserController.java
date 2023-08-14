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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
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
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) {
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
    public ResponseEntity<List<UserResponse>> findUsers(@Valid @RequestBody ListUserRequestDto dto) {
        List<UserResponse> users = userService.findUser(dto).stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();
        return ResponseEntity.ok(users);
    }

}

