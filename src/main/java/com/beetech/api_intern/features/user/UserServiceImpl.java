package com.beetech.api_intern.features.user;

import com.beetech.api_intern.common.exceptions.BadRequestException;
import com.beetech.api_intern.common.exceptions.ConflictException;
import com.beetech.api_intern.common.exceptions.EntityNotFoundException;
import com.beetech.api_intern.common.utils.CustomPasswordEncoder;
import com.beetech.api_intern.common.utils.DateTimeFormatterUtils;
import com.beetech.api_intern.common.utils.StringGenerator;
import com.beetech.api_intern.features.changepasswordtoken.ChangePasswordToken;
import com.beetech.api_intern.features.changepasswordtoken.ChangePasswordTokenRepository;
import com.beetech.api_intern.features.role.RoleEnum;
import com.beetech.api_intern.features.role.RoleRepository;
import com.beetech.api_intern.features.user.dto.ChangePasswordDto;
import com.beetech.api_intern.features.user.dto.ListUserRequestDto;
import com.beetech.api_intern.features.user.exceptions.UserNotFoundException;
import com.beetech.api_intern.security.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CustomPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ChangePasswordTokenRepository changePasswordTokenRepo;

    @Override
    public List<User> findUser(ListUserRequestDto dto) {
        return userRepository.findAll();
    }

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
                .birthDay(DateTimeFormatterUtils.convertBirthdayString(registerDto.getBirthday()))
                .build();
        user.addRole(roleRepository.findRoleByName(RoleEnum.NORMAL));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByLoginId(loginId);
        if (optionalUser.isEmpty()) {
            throw UserNotFoundException.getInstance();
        }
        return optionalUser.get();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::getInstance);
        userRepository.delete(user);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::getInstance);
    }

    @Override
    public void changePassword(Long userId, ChangePasswordDto changePasswordDto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::getInstance);
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Old password is not match!");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public String requestPassword(String email) {
        User user = userRepository.findUserByLoginId(email)
                .orElseThrow(UserNotFoundException::getInstance);

        String token = StringGenerator.getRandomString(20);

        ChangePasswordToken changePasswordToken = ChangePasswordToken
                .builder()
                .user(user)
                .token(token)
                .build();
        changePasswordTokenRepo.save(changePasswordToken);
        return token;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String resetPassword(User user) {
        String newPassword = StringGenerator.getRandomString(32);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return newPassword;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User findUserFromToken(String token) {
        LocalDateTime currentTime = LocalDateTime.now();
        ChangePasswordToken changePasswordToken = changePasswordTokenRepo.findByTokenAndExpireDateIsAfter(token, currentTime)
                .orElseThrow(() -> new EntityNotFoundException("Invalid token!"));
        return changePasswordToken.getUser();
    }

}
