package com.beetech.api_intern.features.user;

import com.beetech.api_intern.common.utils.CustomPasswordEncoder;
import com.beetech.api_intern.features.role.RoleEnum;
import com.beetech.api_intern.features.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDataLoader implements CommandLineRunner {
    /**
     * inject user repository
     */
    private final UserRepository userRepository;

    /**
     * inject role repository
     */
    private final RoleRepository roleRepository;

    /**
     * inject custom password encoder
     */
    private final CustomPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String username = "admin";
        if (this.userRepository.findUserByUsername(username).isEmpty()) {
            User user = User.builder()
                    .loginId("admin@gmail.com")
                    .username(username)
                    .password(passwordEncoder.encode("admin"))
                    .build();
            user.addRole(this.roleRepository.findRoleByName(RoleEnum.ADMIN));
            user.addRole(this.roleRepository.findRoleByName(RoleEnum.NORMAL));
            this.userRepository.save(user);
        }
    }
}