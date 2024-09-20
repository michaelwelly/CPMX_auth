package com.croco.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "group_right")
@Getter
@Setter
public class GroupRight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @NotNull
    @Column(name = "system_part", nullable = false)
    private SystemPart systemPart;

    @Column(name = "fieldno_str")
    private String fieldNo;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @NotNull
    @Column(name = "right_type", nullable = false)
    private RightType fieldRightType;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "title_str")
    private String title;
}
