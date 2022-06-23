package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CategoryResponse {
    private Long categoryId;
    private String categoryTitle;
    private String categoryDescription;
    private boolean flag;
}
