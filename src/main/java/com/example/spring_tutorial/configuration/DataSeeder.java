package com.example.spring_tutorial.configuration;

import com.example.spring_tutorial.domain.entity.ApplicationUser;
import com.example.spring_tutorial.domain.entity.Constants;
import com.example.spring_tutorial.domain.entity.Property;
import com.example.spring_tutorial.repository.ApplicationUserRepository;
import com.example.spring_tutorial.repository.ConstantsRepository;
import com.example.spring_tutorial.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final ApplicationUserRepository userRepository;
    private final ConstantsRepository constantsRepository;
    private final PropertyRepository propertyRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUserTable();
        seedConstantTable();
        seedPropertyTable();
    }

    private void seedPropertyTable() {
        for (int i = 1; i <= 10; i++) {
            propertyRepository.save(Property
                    .builder()
                    .description("dummy data")
                    .price(50 * i)
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
        for (int i = 0; i < mails.size(); i++)
            userRepository.save(ApplicationUser.builder()
                    .email(mails.get(i))
                    .fullName(names.get(i))
                    .password(passwordEncoder.encode("password"))
                    .build());
    }
}
