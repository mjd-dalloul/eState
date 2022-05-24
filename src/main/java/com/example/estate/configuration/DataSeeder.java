package com.example.estate.configuration;

import com.example.estate.domain.entity.ApplicationUser;
import com.example.estate.domain.entity.Constants;
import com.example.estate.domain.entity.Property;
import com.example.estate.domain.entity.Role;
import com.example.estate.repository.ApplicationUserRepository;
import com.example.estate.repository.ConstantsRepository;
import com.example.estate.repository.PropertyRepository;
import com.example.estate.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Profile("!receiver")
public class DataSeeder {

    private final ApplicationUserRepository userRepository;
    private final ConstantsRepository constantsRepository;
    private final PropertyRepository propertyRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
//        seedUserTable();
//        seedConstantTable();
//        seedPropertyTable();
    }

    private void seedPropertyTable() {
        for (int i = 1; i <= 10; i++) {
            propertyRepository.save(Property
                    .builder()
                    .description("dummy data")
                    .price((long) (50 * i))
                    .shares(i)
                    .build());
        }
    }

    private void seedConstantTable() {
        constantsRepository.save(Constants
                .builder()
                .key(AppConstant.share)
                .value(5)
                .build());

        constantsRepository.save(Constants
                .builder()
                .key(AppConstant.profitRate)
                .value(15)
                .build());
    }

    private void seedUserTable() {
        final List<String> mails = List.of(
                "user1@test.com",
                "user2@test.com",
                "user3@test.com",
                "user4@test.com",
                "user5@test.com"
        );
        final List<String> names = List.of(
                "user1",
                "user2",
                "user3",
                "user4",
                "user5"
        );
        Role adminRole = Role.builder()
                .authority(Role.USER_ADMIN)
                .build();
        roleRepository.save(adminRole);
        final HashSet<Role> adminAuthorities = new HashSet<>();
        adminAuthorities.add(adminRole);
        userRepository.save(
          ApplicationUser.builder()
                  .email(mails.get(0))
                  .authorities(adminAuthorities)
                  .fullName(names.get(0))
                  .password(passwordEncoder.encode("password"))
                  .build()
        );
        for (int i = 1; i < mails.size(); i++)
            userRepository.save(ApplicationUser.builder()
                    .email(mails.get(i))
                            .authorities(new HashSet<>())
                    .fullName(names.get(i))
                    .password(passwordEncoder.encode("password"))
                    .build());
    }
}
