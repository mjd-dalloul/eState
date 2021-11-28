package com.example.spring_tutorial.service;

import com.example.spring_tutorial.configuration.AppConstant;
import com.example.spring_tutorial.domain.dto.constants_dto.ConstantViewModel;
import com.example.spring_tutorial.domain.entity.Constants;
import com.example.spring_tutorial.domain.exception.NotFoundException;
import com.example.spring_tutorial.repository.ConstantsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConstantService {
    private final ConstantsRepository repository;
//todo
    @CachePut(value = AppConstant.appMainCacheName, condition = "#constantViewModel.key==")
    public void setNewValueForConstant(ConstantViewModel constantViewModel) {
        final Constants constant = repository.findByKey(constantViewModel.getKey());
        if (constant == null) {
            throw new NotFoundException("Can not found a key with this value");
        } else {
            constant.setValue(constantViewModel.getValue());
            repository.save(constant);
        }
    }
}
