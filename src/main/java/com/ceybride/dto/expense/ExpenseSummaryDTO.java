package com.ceybride.dto.expense;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExpenseSummaryDTO {
    private BigDecimal estimatedTotal;
    private BigDecimal actualTotal;
    private BigDecimal paidTotal;
    private BigDecimal remainingTotal;
}
