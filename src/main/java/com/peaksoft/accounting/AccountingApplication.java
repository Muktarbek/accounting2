package com.peaksoft.accounting;

import com.peaksoft.accounting.db.entity.BusinessAreaEntity;
import com.peaksoft.accounting.db.entity.RoleEntity;
import com.peaksoft.accounting.db.entity.ServiceTypeEntity;
import com.peaksoft.accounting.db.repository.BusinessAreaRepository;
import com.peaksoft.accounting.db.repository.RoleRepository;
import com.peaksoft.accounting.db.repository.ServiceTypeRepository;
import com.peaksoft.accounting.enums.ServiceType;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@OpenAPIDefinition
@EnableScheduling
@RequiredArgsConstructor
public class AccountingApplication {
    private final RoleRepository roleRepository;
    private final BusinessAreaRepository repository;
    private final ServiceTypeRepository typeRepository;
    public static void main(String[] args) {
        SpringApplication.run(AccountingApplication.class, args);
    }

    @PostConstruct
    private void saveRole(){
        RoleEntity role = new RoleEntity();
        role.setRole_id(1L);
        role.setName("MY_ACCOUNT_ADMIN");
        roleRepository.save(role);
        RoleEntity role2 = new RoleEntity();
        role2.setRole_id(2L);
        role2.setName("MY_ACCOUNT_EDITOR");
        roleRepository.save(role2);
        BusinessAreaEntity business = new BusinessAreaEntity();
        business.setArea("Business");
        repository.save(business);
        BusinessAreaEntity business1 = new BusinessAreaEntity();
        business1.setArea("Education");
        repository.save(business1);
        ServiceTypeEntity typeEntity = new ServiceTypeEntity();
        typeEntity.setServiceType(ServiceType.SERVICE);
        typeRepository.save(typeEntity);
        ServiceTypeEntity typeEntity1 = new ServiceTypeEntity();
        typeEntity1.setServiceType(ServiceType.PRODUCT);
        typeRepository.save(typeEntity1);
    }
}
