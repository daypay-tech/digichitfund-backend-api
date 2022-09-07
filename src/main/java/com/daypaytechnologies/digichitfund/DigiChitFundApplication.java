package com.daypaytechnologies.digichitfund;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@OpenAPIDefinition(info = @Info(title = "Employees API", version = "2.0", description = "Employees Information"))
//@SecurityScheme(name = "navpulseuk", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class DigiChitFundApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigiChitFundApplication.class, args);
    }
}
