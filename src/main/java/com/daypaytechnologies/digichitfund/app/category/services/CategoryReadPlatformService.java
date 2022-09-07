package com.daypaytechnologies.digichitfund.app.category.services;

import com.daypaytechnologies.digichitfund.app.category.data.CategoryData;

import java.util.List;

public interface CategoryReadPlatformService {

    List<CategoryData> fetchAll();

    CategoryData fetchByCategoryId(Long categoryId);
}
