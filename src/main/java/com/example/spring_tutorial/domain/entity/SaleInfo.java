package com.example.spring_tutorial.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Embeddable
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleInfo {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private ApplicationUser buyerInfo;
    private Integer salePrice;
    @Temporal(TemporalType.TIMESTAMP)
    private Date saleDate;
}
