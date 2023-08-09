package com.beetech.api_intern.features.user;

import com.beetech.api_intern.features.user.dto.ChangePasswordDto;
import com.beetech.api_intern.features.user.dto.ListUserRequestDto;
import com.beetech.api_intern.security.dto.RegisterDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findUser(ListUserRequestDto dto);
    void register(RegisterDto registerDto);
    void deleteUser(User user);

    @Override
    @Transactional
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void deleteUserById(Long userId);

    User findById(Long userId);
    void changePassword(Long userId, ChangePasswordDto changePasswordDto);

    String resetPassword(User user);

    String requestPassword(String email);
    User findUserFromToken(String token);
}