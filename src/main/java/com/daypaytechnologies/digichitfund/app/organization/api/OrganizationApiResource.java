package com.daypaytechnologies.digichitfund.app.organization.api;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.app.organization.data.OrganizationData;
import com.daypaytechnologies.digichitfund.app.organization.request.CreateOrganizationRequest;
import com.daypaytechnologies.digichitfund.app.organization.request.UpdateOrganizationRequest;
import com.daypaytechnologies.digichitfund.app.organization.services.OrganizationReadPlatformService;
import com.daypaytechnologies.digichitfund.app.organization.services.OrganizationWritePlatformService;
import com.daypaytechnologies.digichitfund.app.user.constants.RoleConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "OrganizationApiResource")
public class OrganizationApiResource {

    private final OrganizationWritePlatformService organizationWritePlatformService;

    private final OrganizationReadPlatformService organizationReadPlatformService;

    @PostMapping
    @RolesAllowed({RoleConstants.ROLE_SUPER_ADMIN})
    public Response saveOrganization(@RequestBody CreateOrganizationRequest request){
        return this.organizationWritePlatformService.save(request);
    }

    @GetMapping("/{id}")
    @RolesAllowed({RoleConstants.ROLE_SUPER_ADMIN})
    public OrganizationData getOrganization(@PathVariable Long id){
        return this.organizationReadPlatformService.fetchById(id);
    }

    @GetMapping
    public List<OrganizationData> getAllOrganizations(){
        return this.organizationReadPlatformService.fetchAll();
    }

    @PutMapping("/{id}")
    @RolesAllowed({RoleConstants.ROLE_SUPER_ADMIN})
    public Response updateOrganization(@PathVariable Long id, @RequestBody UpdateOrganizationRequest request){
        return this.organizationWritePlatformService.update(id, request);
    }
}
