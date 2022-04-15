package com.peaksoft.accounting.service;

import com.peaksoft.accounting.db.entity.BusinessAreaEntity;
import com.peaksoft.accounting.api.payload.BusinessAreaResponse;
import com.peaksoft.accounting.db.repository.BusinessAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessAreaService {

    private final BusinessAreaRepository businessAreaRepository;

    public List<BusinessAreaResponse> getAllAreas(){
        return convert(businessAreaRepository.findAll());
    }
    public BusinessAreaResponse convert(BusinessAreaEntity areaEntity){
        return BusinessAreaResponse.builder()
                .businessAreaId(areaEntity.getBusiness_area_id())
                .area(areaEntity.getArea())
                .build();
    }

    public List<BusinessAreaResponse> convert(List<BusinessAreaEntity> areaEntities){
        List<BusinessAreaResponse> responses = new ArrayList<>();
        for (BusinessAreaEntity areaEntity : areaEntities) {
            responses.add(convert(areaEntity));
        }
        return responses;
    }
}
