package com.example.spring_tutorial;

import com.example.spring_tutorial.configuration.AppConstant;
import com.example.spring_tutorial.domain.entity.Constants;
import com.example.spring_tutorial.domain.entity.Property;
import com.example.spring_tutorial.repository.ConstantsRepository;
import com.example.spring_tutorial.repository.PropertyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PropertyRepositoryTests {

    @Autowired
    private PropertyRepository repository;
    @Autowired
    private ConstantsRepository constantsRepository;

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
        Property deletedProperty =  repository.findById(id).orElse(null);
        Assertions.assertThat(deletedProperty).isEqualTo(null);
    }

    @Test
    public void optimisticLockTest() throws Exception  {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        final Long propertyId = repository.findAll().get(0).getId();
        System.out.println("VERSION IS");
        System.out.println(repository.findAll().get(0).getPrice());
        executorService.execute(() -> {
            Property property = repository.findById(propertyId).get();
            property.setPrice(2020L);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            property = repository.save(property);
            //Assertions.assertThat(property.getPrice()).isEqualTo(2020L);

        });
        executorService.execute(() -> {
            Property property = repository.findById(propertyId).get();
            property.setPrice(3030L);
            repository.save(property);

        });

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        final Property p = repository.findById(propertyId).get();
        Assertions.assertThat(p.getPrice()).isEqualTo(2020L);
    }
}
