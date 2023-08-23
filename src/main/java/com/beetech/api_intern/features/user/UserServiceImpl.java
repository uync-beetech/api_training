package com.beetech.api_intern.features.user;

import com.beetech.api_intern.common.exceptions.BadRequestException;
import com.beetech.api_intern.common.exceptions.ConflictException;
import com.beetech.api_intern.common.exceptions.EntityNotFoundException;
import com.beetech.api_intern.common.utils.CustomPasswordEncoder;
import com.beetech.api_intern.common.utils.DateTimeFormatterUtils;
import com.beetech.api_intern.common.utils.StringGenerator;
import com.beetech.api_intern.features.changepasswordtoken.ChangePasswordToken;
import com.beetech.api_intern.features.changepasswordtoken.ChangePasswordTokenRepository;
import com.beetech.api_intern.features.email.MailService;
import com.beetech.api_intern.features.role.RoleEnum;
import com.beetech.api_intern.features.role.RoleRepository;
import com.beetech.api_intern.features.user.dto.ChangePasswordRequest;
import com.beetech.api_intern.features.user.dto.ListUserRequest;
import com.beetech.api_intern.features.user.exceptions.UserNotFoundException;
import com.beetech.api_intern.security.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private final MailService mailService;

    @Override
    public List<User> findUser(ListUserRequest dto) {
        // convert from-to birthday from string format yyyyMMdd-yyyyMMdd
        LocalDate from = DateTimeFormatterUtils.convertBirthdayString(dto.getBirthDay().split("-")[0]);
        LocalDate to = DateTimeFormatterUtils.convertBirthdayString(dto.getBirthDay().split("-")[1]);

        // find list user was born (>= "from") and (<= "to")
        return userRepository.findAllByBirthDayGreaterThanEqualAndBirthDayLessThanEqual(from, to);
    }

    @Override
    public void register(RegisterDto registerDto) {
        // check user is not exist
        boolean isExistedUser = userRepository.existsByLoginId(registerDto.getLoginId()) || userRepository.existsByUsername(registerDto.getUsername());
        if (isExistedUser) {
            throw new ConflictException("Username or loginId already existed!");
        }

        // create user from request data
        User user = User.builder()
                .loginId(registerDto.getLoginId())
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .birthDay(DateTimeFormatterUtils.convertBirthdayString(registerDto.getBirthday()))
                .build();
        // set default role normal for user
        user.addRole(roleRepository.findRoleByName(RoleEnum.NORMAL));
        // save user to database
        userRepository.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUser(User user) {
        // set deleted
        user.setDeleted(9);
        // back up old loginId
        user.setOldLoginId(user.getLoginId());
        // delete login id
        user.setLoginId(null);
        // save user to database
        userRepository.save(user);
    }

    @Override
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
    public void changePassword(Long userId, ChangePasswordRequest changePasswordDto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::getInstance);
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Old password is not match!");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public String requestPassword(String email) {
        // find user by login id
        User user = userRepository.findUserByLoginId(email)
                .orElseThrow(UserNotFoundException::getInstance);

        // generate random token string with 20 chars
        String token = StringGenerator.getRandomString(20);

        // create change password token
        ChangePasswordToken changePasswordToken = ChangePasswordToken
                .builder()
                .user(user)
                .token(token)
                .build();
        // save change password token to database
        changePasswordTokenRepo.save(changePasswordToken);
        return token;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String resetPassword(User user) {
        // generate random password with 32 chars
        String newPassword = StringGenerator.getRandomString(32);
        // encode and set user password
        user.setPassword(passwordEncoder.encode(newPassword));
        // save user
        userRepository.save(user);
        return newPassword;
    }


    @Override
    public User findUserFromToken(String token) {
        // get current time
        LocalDateTime currentTime = LocalDateTime.now();
        // find token not expired yet
        ChangePasswordToken changePasswordToken = changePasswordTokenRepo.findByTokenAndExpireDateIsAfter(token, currentTime)
                .orElseThrow(() -> new EntityNotFoundException("Invalid token!"));
        return changePasswordToken.getUser();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void lockUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setStatus(9);
        userRepository.save(user);

        mailService.sendEmail(user.getLoginId(), "Account locked notification", "Your account has been locked by the admin.");
    }

}
