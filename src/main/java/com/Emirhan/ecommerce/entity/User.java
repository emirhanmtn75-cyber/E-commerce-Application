package com.Emirhan.ecommerce.entity;

import com.Emirhan.ecommerce.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}
