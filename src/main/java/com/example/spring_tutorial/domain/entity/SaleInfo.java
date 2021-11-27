package com.example.spring_tutorial.domain.entity;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Embeddable
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleInfo {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_info_id")
    private ApplicationUser buyerInfo;
    private Integer salePrice;
    private Date saleDate;
}
