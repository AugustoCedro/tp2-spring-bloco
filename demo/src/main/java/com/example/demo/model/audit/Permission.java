package com.example.demo.model.audit;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Table(name = "permissions",schema = "audit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String code;

    @Column(name = "descricao",nullable = false)
    private String description;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;
}
