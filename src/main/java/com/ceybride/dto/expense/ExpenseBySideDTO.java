package com.ceybride.dto.expense;

import com.ceybride.enums.ResponsibleSide;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExpenseBySideDTO {
    private ResponsibleSide responsibleSide;
    private BigDecimal totalAmount;
}
