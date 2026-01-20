package com.geneai.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "persons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Builder.Default
    private UUID uuid = UUID.randomUUID();

    private String firstName;
    private String lastName;

    private LocalDate birthDate;
    private String residence;

    private LocalDate deathDate;
    private String burialPlace;

    @ManyToMany
    @JoinTable(
        name = "person_parents",
        joinColumns = @JoinColumn(name = "child_id"),
        inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    @Builder.Default
    private Set<Person> parents = new HashSet<>();

    @ManyToMany(mappedBy = "parents")
    @Builder.Default
    private Set<Person> children = new HashSet<>();

    @ManyToMany(mappedBy = "participants")
    @Builder.Default
    private Set<Marriage> marriages = new HashSet<>();

    public void addParent(Person parent) {
        this.parents.add(parent);
        parent.getChildren().add(this);
    }

    public void addChild(Person child) {
        this.children.add(child);
        child.getParents().add(this);
    }
}
