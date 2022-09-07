package com.daypaytechnologies.digichitfund.app.category.domain;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryRepositoryWrapper {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Category findOneWithNotFoundDetection(final String categoryName) {
        return this.categoryRepository.findByCategoryName(categoryName).orElseThrow(() -> new NotFoundException("Category  Not found " + categoryName));
    }

    @Transactional(readOnly = true)
    public Category findOneWithNotFoundDetection(final Long categoryId) {
        return this.categoryRepository.findByCategoryId(categoryId).orElseThrow(() -> new NotFoundException("Category  Not found " + categoryId));
    }
}
