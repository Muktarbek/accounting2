package com.peaksoft.accounting.api.payload;

import lombok.*;;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {

    private Long id;
    private String company_name;

}
