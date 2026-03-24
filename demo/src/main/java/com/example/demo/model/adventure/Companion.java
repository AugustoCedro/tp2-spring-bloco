package com.example.demo.model.adventure;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "companheiros",schema = "aventura")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Companion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome",nullable = false)
    private String name;

    @Column(name = "especie",nullable = false)
    private Specie specie;

    @Column(name = "lealdade",nullable = false)
    private Integer loyalty;

    @OneToOne(optional = false)
    @JoinColumn(name = "aventureiro_id",nullable = false,unique = true)
    private Adventurer adventurer;

    public Companion(String name, Specie specie, Integer loyalty, Adventurer adventurer) {
        this.name = name;
        this.specie = specie;
        this.loyalty = loyalty;
        this.adventurer = adventurer;
    }
}
