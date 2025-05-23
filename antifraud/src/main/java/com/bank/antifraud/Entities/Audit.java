package com.bank.antifraud.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Сущность для аудита изменений в подозрительных операциях.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audit", schema = "anti_fraud")
public class Audit {
    private static final int ENTITY_TYPE_MAX_LENGTH = 40;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_type", length = ENTITY_TYPE_MAX_LENGTH)
    private String entityType;

    @Column(name = "operation_type")
    private String operationType;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "new_entity_json", columnDefinition = "text")
    private String newEntityJson;

    @Column(name = "entity_json", columnDefinition = "text")
    private String entityJson;
}

