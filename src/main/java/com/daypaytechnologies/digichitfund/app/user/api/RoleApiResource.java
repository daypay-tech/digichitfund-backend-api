package com.daypaytechnologies.digichitfund.app.user.api;

import com.daypaytechnologies.digichitfund.app.user.data.RoleData;
import com.daypaytechnologies.digichitfund.app.user.services.RoleReadPlatformService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
@Tag(name = "RoleApiResource")
public class RoleApiResource {

    private final RoleReadPlatformService roleReadPlatformService;

    @GetMapping
    public List<RoleData> fetchAll(){
        return this.roleReadPlatformService.fetchAll();
    }

}
