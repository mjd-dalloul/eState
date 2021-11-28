package com.example.spring_tutorial.service;

import com.example.spring_tutorial.configuration.AppConstant;
import com.example.spring_tutorial.domain.dto.constants_dto.ConstantViewModel;
import com.example.spring_tutorial.domain.entity.Constants;
import com.example.spring_tutorial.domain.exception.NotFoundException;
import com.example.spring_tutorial.repository.ConstantsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstantService {
    private final ConstantsRepository repository;

    @CachePut(value = AppConstant.appMainCacheName, key = "#constantViewModel.getKey()")
    public Constants setNewValueForConstant(ConstantViewModel constantViewModel) {
        final Constants constant = repository.findById(constantViewModel.getId()).orElseThrow(
                () -> new NotFoundException("Can not found a key with this value")
        );
        constant.setValue(constantViewModel.getValue());
        repository.save(constant);
        return constant;
    }

    public List<Constants> fetchConstants() {
        return repository.findAll();
    }
}
