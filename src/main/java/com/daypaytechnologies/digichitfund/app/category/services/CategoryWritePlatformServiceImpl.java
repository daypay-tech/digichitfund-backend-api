package com.daypaytechnologies.digichitfund.app.category.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NavPulseApplicationException;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformDataIntegrityException;
import com.daypaytechnologies.digichitfund.app.category.data.CategoryDataValidator;
import com.daypaytechnologies.digichitfund.app.category.domain.Category;
import com.daypaytechnologies.digichitfund.app.category.domain.CategoryRepository;
import com.daypaytechnologies.digichitfund.app.category.domain.CategoryRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.category.request.CreateCategoryRequest;
import com.daypaytechnologies.digichitfund.app.category.request.UpdateCategoryRequest;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import com.daypaytechnologies.digichitfund.security.services.PlatformSecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryWritePlatformServiceImpl implements CategoryWritePlatformService {

    private final CategoryDataValidator categoryDataValidator;

    private final CategoryRepository categoryRepository;

    private final CategoryRepositoryWrapper categoryRepositoryWrapper;

    private final PlatformSecurityContext platformSecurityContext;

    @Override
    @Transactional
    public Response saveCategory(CreateCategoryRequest createCategoryRequest) {
        try {
            final AdministrationUser userAccount = this.platformSecurityContext.validateAdministrationUser();
            this.categoryDataValidator.validateCreateCategory(createCategoryRequest);
            final Category category = Category.from(createCategoryRequest, userAccount);
            categoryRepository.saveAndFlush(category);
            return Response.of(category.getId());
        } catch (DataIntegrityViolationException e) {
            throw new PlatformDataIntegrityException("error.duplicate.data", String.format("Category %s already exist", createCategoryRequest.getCategoryName()));
        } catch (Exception e) {
            throw new NavPulseApplicationException(e.getMessage());
        }
    }


    @Override
    @Transactional
    public Response updateCategory(Long id, UpdateCategoryRequest request) {
        final AdministrationUser userAccount = this.platformSecurityContext.validateAdministrationUser();
        final Category oldCategory = categoryRepositoryWrapper.findOneWithNotFoundDetection(id);
        oldCategory.setCategoryName(request.getCategoryName());
        oldCategory.setCreatedBy(userAccount);
        oldCategory.setUpdatedBy(userAccount);
        this.categoryRepository.saveAndFlush(oldCategory);
        return Response.of(oldCategory.getId());
    }

    @Override
    public Category delete(Long id) {
        final Category category = this.categoryRepositoryWrapper.findOneWithNotFoundDetection(id);
        category.setIsDeleted(true);
        category.setDeletedOn(LocalDate.now());
        category.setDeletedBy(1);
        this.categoryRepository.saveAndFlush(category);
        return category;
    }
}
