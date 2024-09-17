package com.croco.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "group_right")
@Getter
@Setter
public class GroupRight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "system_part_id")
    private SystemPart systemPart;

    @Column(name = "fieldno_str")
    private String fieldNo;

    @ManyToOne
    @JoinColumn(name = "field_right_type_id")
    private RightType fieldRightType;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "title_str")
    private String title;

    // Геттеры и сеттеры
}
