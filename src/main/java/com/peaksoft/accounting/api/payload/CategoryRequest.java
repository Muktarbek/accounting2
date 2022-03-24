package com.peaksoft.accounting.api.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    private String category_title;
    private String category_description;
}
