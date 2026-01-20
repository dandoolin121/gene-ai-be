package com.geneai.infrastructure.adapters.output.persistence;

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
class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

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
    private Set<PersonEntity> parents = new HashSet<>();

    @ManyToMany(mappedBy = "parents")
    @Builder.Default
    private Set<PersonEntity> children = new HashSet<>();

    @ManyToMany(mappedBy = "participants")
    @Builder.Default
    private Set<MarriageEntity> marriages = new HashSet<>();
}
