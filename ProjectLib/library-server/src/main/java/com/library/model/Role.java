package com.library.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles") // Уже указано
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String roleName;
}
