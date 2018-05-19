package com.crud.tasks.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class ExampleInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> appInfo = new HashMap<>();
        appInfo.put("name","Kodilla Application");
        appInfo.put("description","Kodilla Course App");
        appInfo.put("version","1.1.0");
        Map<String, String> ownerInfo = new HashMap<>();
        ownerInfo.put("name","John");
        ownerInfo.put("surname","Doe");
        Map<String, String> addressInfo = new HashMap<>();
        addressInfo.put("street","Super cool");
        addressInfo.put("number","42");
        Map<String, String> companyInfo = new HashMap<>();
        companyInfo.put("name","TaskCrudApps");
        companyInfo.put("goal","Some goal");
        companyInfo.put("email","task@crud.com");
        companyInfo.put("phone","439282910");

        builder.withDetail("app", appInfo);
        builder.withDetail("app", builder.withDetail("owner",ownerInfo));
        builder.withDetail("administrator", Collections.singletonMap("email","john.doe@mail.com"));
        builder.withDetail("administrator",builder.withDetail("address",addressInfo));
        builder.withDetail("company",companyInfo);

    }

}