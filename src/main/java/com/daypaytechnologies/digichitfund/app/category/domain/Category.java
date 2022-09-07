package com.daypaytechnologies.digichitfund.app.category.domain;

import com.daypaytechnologies.digichitfund.app.category.request.CreateCategoryRequest;
import com.daypaytechnologies.digichitfund.app.core.baseentity.AdministrationUserBaseEntity;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Category")
@Table(name = "category")
@Data
public class Category extends AdministrationUserBaseEntity {

    @Id
    @GeneratedValue(generator = "np_category_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "np_category_gen", sequenceName = "np_category_gen_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "is_deleted", nullable = true)
    @Basic(optional = true)
    private Boolean isDeleted;

    @Column(name = "deleted_on", nullable = true)
    @Basic(optional = true)
    private LocalDate deletedOn;

    @Column(name = "deleted_by", nullable = true)
    @Basic(optional = true)
    private int deletedBy;

    public static Category from(final CreateCategoryRequest createCategoryRequest,
                                final AdministrationUser userAccount) {
        final Category category = new Category();
        category.setCategoryName(createCategoryRequest.getCategoryName());
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        category.setCreatedBy(userAccount);
        category.setUpdatedBy(userAccount);
        return category;

    }
}
