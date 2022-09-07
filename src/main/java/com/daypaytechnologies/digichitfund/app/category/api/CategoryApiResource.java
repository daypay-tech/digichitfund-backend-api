package com.daypaytechnologies.digichitfund.app.category.api;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.app.category.data.CategoryData;
import com.daypaytechnologies.digichitfund.app.category.domain.Category;
import com.daypaytechnologies.digichitfund.app.category.request.CreateCategoryRequest;
import com.daypaytechnologies.digichitfund.app.category.request.UpdateCategoryRequest;
import com.daypaytechnologies.digichitfund.app.category.services.CategoryReadPlatformService;
import com.daypaytechnologies.digichitfund.app.category.services.CategoryWritePlatformService;
import com.daypaytechnologies.digichitfund.app.user.constants.RoleConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "CategoryApiResource")
public class CategoryApiResource {

    private final CategoryReadPlatformService categoryReadPlatformService;

    private final CategoryWritePlatformService categoryWritePlatformService;

    @PostMapping
    @RolesAllowed({RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_SUPER_ADMIN})
    public Response saveCategory(@RequestBody CreateCategoryRequest request){
        return this.categoryWritePlatformService.saveCategory(request);
    }
    @GetMapping
    @RolesAllowed({ RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MEMBER, RoleConstants.ROLE_SUPER_ADMIN })
    public List<CategoryData> fetchAll(){
        return this.categoryReadPlatformService.fetchAll();
    }

    @GetMapping("/{id}")
    @RolesAllowed({ RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_MEMBER })
    public CategoryData  fetchByCategoryId(@PathVariable Long id) {
        return this.categoryReadPlatformService.fetchByCategoryId(id);
    }

    @PutMapping("/{id}")
    @RolesAllowed({RoleConstants.ROLE_ADMIN})
    public Response updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequest request){
        return this.categoryWritePlatformService.updateCategory(id, request);
    }
    @DeleteMapping("/{id}")
    @RolesAllowed({RoleConstants.ROLE_ADMIN})
    public Response deleteCategoryById(@PathVariable Long id) {
        final Category category = this.categoryWritePlatformService.delete(id);
        return Response.of(category.getId());
    }
}
