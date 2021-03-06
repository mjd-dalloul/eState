package com.example.estate;

import com.example.estate.configuration.AppConstant;
import com.example.estate.domain.entity.ApplicationUser;
import com.example.estate.domain.entity.Constants;
import com.example.estate.domain.entity.Property;
import com.example.estate.domain.entity.SaleInfo;
import com.example.estate.repository.ApplicationUserRepository;
import com.example.estate.repository.ConstantsRepository;
import com.example.estate.repository.PropertyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PropertyRepositoryTests {

    @Autowired
    private PropertyRepository repository;
    @Autowired
    private ConstantsRepository constantsRepository;
    @Autowired
    private ApplicationUserRepository userRepository;

    @BeforeAll
    public void mockData() {
        constantsRepository.save(Constants.builder()
                .key(AppConstant.share)
                .value(7)
                .build());
        constantsRepository.save(Constants.builder()
                .key(AppConstant.profitRate)
                .value(5)
                .build());
        repository.save(Property.builder()
                .id(1L)
                .description("test")
                .price(213L)
                .build());
        userRepository.save(
                ApplicationUser.builder()
                        .fullName("Mjd")
                        .email("majed.dalloul79@gmail.com")
                        .password("password")
                        .authorities(new HashSet<>())
                        .build()
        );
        userRepository.save(
                ApplicationUser.builder()
                        .fullName("Mjd 2")
                        .email("majed.dalloul@gmail.com")
                        .password("password")
                        .authorities(new HashSet<>())
                        .build()
        );
    }

    @Test
    public void addPropertyTest() {
        Property property = Property.builder()
                .description("test")
                .shares(constantsRepository.findByKey(AppConstant.share).getValue())
                .price(213L)
                .build();
        repository.save(property);
        Assertions.assertThat(property.getId()).isGreaterThan(0);
    }

    @Test
    public void getListOfPropertiesTest() {
        final List<Property> property = repository.findAll();
        Assertions.assertThat(property.size()).isGreaterThan(0);
    }

    @Test
    public void testMockedData() {
        final List<Constants> constants = constantsRepository.findAll();
        Assertions.assertThat(constants.size()).isGreaterThan(0);
    }

    @Test
    public void editProperty() {
        Property p = repository.findAll().get(0);
        p.setDescription("Just Edit that");
        repository.save(p);
        p = repository.findAll().get(0);
        Assertions.assertThat(p.getDescription()).isEqualTo("Just Edit that");
    }

    @Test
    public void deleteProperty() {
        Property p = repository.findAll().get(0);
        Long id = p.getId();
        repository.delete(p);
        Property deletedProperty = repository.findById(id).orElse(null);
        Assertions.assertThat(deletedProperty).isEqualTo(null);
    }

    @Test
    public void optimisticLockTest() {
        final Long id = repository.findAll().get(0).getId();
        final List<Long> users = userRepository.findAll().stream()
                .map(ApplicationUser::getId).collect(Collectors.toList());
        Property property = repository.findById(id).get();
        Property firstRequest = copyProperty(property);
        Property secondRequest = copyProperty(property);

        secondRequest.setSaleInfo(SaleInfo.builder()
                .saleDate(new Date(System.currentTimeMillis()))
                .salePrice(2020L)
                .buyerInfo(ApplicationUser.builder()
                        .id(users.get(0))
                        .build())
                .build());
        firstRequest.setSaleInfo(SaleInfo.builder()
                .saleDate(new Date(System.currentTimeMillis()))
                .salePrice(3030L)
                .buyerInfo(ApplicationUser.builder()
                        .id(users.get(1))
                        .build())
                .build());
        firstRequest = repository.save(firstRequest);
        repository.flush();
        try {
            secondRequest = repository.save(secondRequest);
        } catch (Exception e) {
            Assertions.assertThat(repository.findById(id)
                    .get().getSaleInfo().getBuyerInfo()
                    .getId()).isEqualTo(users.get(1));
        }
        Assertions.assertThat(repository.findById(id)
                .get().getSaleInfo().getBuyerInfo()
                .getId()).isNotEqualTo(users.get(0));
    }

    private Property copyProperty(Property property) {
        return Property.builder()
                .id(property.getId())
                .description(property.getDescription())
                .price(property.getPrice())
                .createdBy(property.getCreatedBy())
                .updatedBy(property.getUpdatedBy())
                .updatedOn(property.getUpdatedOn())
                .saleInfo(property.getSaleInfo())
                .shares(property.getShares())
                .version(property.getVersion())
                .build();
    }
}
