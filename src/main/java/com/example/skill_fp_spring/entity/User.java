package com.example.skill_fp_spring.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double balance;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Operation> operations = new ArrayList<>();
}