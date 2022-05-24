package com.example.estate.domain.entity;

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
    @JoinColumn(foreignKey = @ForeignKey(name = "buyer_id"))
    private ApplicationUser buyerInfo;
    private Long salePrice;
    @Temporal(TemporalType.TIMESTAMP)
    private Date saleDate;
}
