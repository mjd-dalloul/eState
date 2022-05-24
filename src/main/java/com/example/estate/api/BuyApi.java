package com.example.estate.api;

import com.example.estate.configuration.AppConstant;
import com.example.estate.domain.dto.BaseResponse;
import com.example.estate.domain.dto.auth_dto.CustomUserSecurity;
import com.example.estate.domain.dto.property.BuyFormModel;
import com.example.estate.domain.dto.property.BuyerInfo;
import com.example.estate.domain.entity.Property;
import com.example.estate.service.BuyingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/buy")
@RequiredArgsConstructor
@Profile("!receiver")
public class BuyApi {
    private final BuyingService buyingService;

    @PutMapping("")
    public ResponseEntity<BaseResponse<Property>> buyProperty(@RequestParam Long id, @RequestBody @Valid BuyerInfo buyerInfo) {
        buyingService.buyProperty(buyerInfo, id);
        return ResponseEntity.ok().body(
                BaseResponse.<Property>builder()
                        .statusCode(HttpStatus.OK.value())
                        .success(true)
                        .build()
        );
    }


    @GetMapping("")
    public ResponseEntity<BaseResponse<BuyFormModel>> fetchFormDetails(@RequestParam Long id) {
        return ResponseEntity.ok().body(
                BaseResponse.<BuyFormModel>builder()
                        .statusCode(HttpStatus.OK.value())
                        .success(true)
                        .data(buyingService.fetchBuyFormData(
                                id,
                                ((CustomUserSecurity) SecurityContextHolder
                                        .getContext()
                                        .getAuthentication()
                                        .getPrincipal())
                                        .getUser().getFullName()
                                )
                        )
                        .build()
        );
    }
@Autowired
    CacheManager cacheManager;
    @GetMapping("/cache")
    public void tmp() {
        System.out.println("CACHE");
        System.out.println(cacheManager.getCache(AppConstant.appMainCacheName).get(AppConstant.share).get());
        System.out.println(cacheManager.getCache(AppConstant.appMainCacheName).get(AppConstant.profitRate));
    }
}
