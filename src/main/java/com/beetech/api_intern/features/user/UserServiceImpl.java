package com.beetech.api_intern.features.user;

import com.beetech.api_intern.common.exceptions.ConflictException;
import com.beetech.api_intern.common.utils.CustomPasswordEncoder;
import com.beetech.api_intern.common.utils.DateTimeFormatterUtils;
import com.beetech.api_intern.security.dto.RegisterDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The type User service.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CustomPasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterDto registerDto) {
        boolean isExistedUser = userRepository.existsByLoginId(registerDto.getLoginId()) || userRepository.existsByUsername(registerDto.getUsername());
        if (isExistedUser) {
            throw new ConflictException("Username or loginId already existed!");
        }

        User user = User.builder()
                .loginId(registerDto.getLoginId())
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .birthday(DateTimeFormatterUtils.convertStringToLocalDate(registerDto.getBirthday()))
                .build();
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByLoginId(loginId);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found.");
        }
        return optionalUser.get();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        userRepository.delete(user);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

}
