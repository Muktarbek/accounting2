package com.peaksoft.accounting;

import com.peaksoft.accounting.db.entity.RoleEntity;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@OpenAPIDefinition
@EnableScheduling
public class AccountingApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountingApplication.class, args);
    }
    @PostConstruct
    private void saveRole(){
        RoleEntity role = new RoleEntity();
        role.setRole_id(1L);
        role.setName("MY_ACCOUNT_ADMIN");
    }
}
