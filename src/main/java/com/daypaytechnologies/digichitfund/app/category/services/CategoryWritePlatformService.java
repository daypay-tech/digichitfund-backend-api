package com.daypaytechnologies.digichitfund.app.category.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.app.category.domain.Category;
import com.daypaytechnologies.digichitfund.app.category.request.CreateCategoryRequest;
import com.daypaytechnologies.digichitfund.app.category.request.UpdateCategoryRequest;

public interface CategoryWritePlatformService {

    Response saveCategory(CreateCategoryRequest request);

    Response updateCategory(Long id, UpdateCategoryRequest request);

    Category delete(Long id);
}
