package com.example.estate.service;

import com.example.estate.configuration.AppConstant;
import com.example.estate.domain.dto.property.BuyFormModel;
import com.example.estate.domain.dto.property.BuyerInfo;
import com.example.estate.domain.entity.ApplicationUser;
import com.example.estate.domain.entity.Property;
import com.example.estate.domain.entity.SaleInfo;
import com.example.estate.domain.exception.NotFoundException;
import com.example.estate.repository.ConstantsRepository;
import com.example.estate.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Transactional
@RequiredArgsConstructor
public class BuyingService {
    private final PropertyRepository repository;
    private final ConstantsRepository constantsRepository;

    //todo optimize with one query
    public void buyProperty(BuyerInfo info, Long propertyId) {
        final Property p = repository.findById(propertyId).orElseThrow(
                () -> new NotFoundException("Can not found property with this id")
        );
        p.setSaleInfo(SaleInfo.builder()
                .salePrice(info.getPrice())
                .buyerInfo(
                        ApplicationUser.builder()
                                .id(info.getId())
                                .build()
                )
                .saleDate(new Date(System.currentTimeMillis()))
                .build()
        );
        repository.save(p);
        repository.flush();
    }

    public BuyFormModel fetchBuyFormData(Long propertyId, String userFullName) {
        final Property property = repository.findById(propertyId).orElseThrow(
                () -> new NotFoundException("Can not found property with this id")
        );
        if (property.getSaleInfo() == null) {
            return BuyFormModel.builder()
                    .name(userFullName)
                    .price(property.getPrice())
                    .build();
        } else {
            return BuyFormModel.builder()
                    .price(
                            calcDefaultPrice(
                                    property.getSaleInfo().getSalePrice(), constantsRepository
                                    .findByKey(AppConstant.profitRate).getValue()
                            )
                    )
                    .name(property.getSaleInfo().getBuyerInfo().getFullName())
                    .build();
        }
    }

    private Long calcDefaultPrice(Long price, Integer profitRate) {
        double profitAmount = (price * (profitRate / 100.0));
        return Math.round(price + profitAmount);
    }
}
