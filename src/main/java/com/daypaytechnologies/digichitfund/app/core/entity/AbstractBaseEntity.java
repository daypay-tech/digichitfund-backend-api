package com.daypaytechnologies.digichitfund.app.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class AbstractBaseEntity {
    @Column(name = "created_at", nullable = false)
    @Basic(optional = false)
    protected LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @Basic(optional = false)
    protected LocalDateTime updatedAt;

    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
