package com.geneai.infrastructure.adapters.output.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "marriages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class MarriageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

    @ManyToMany
    @JoinTable(
        name = "person_marriages",
        joinColumns = @JoinColumn(name = "marriage_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    @Builder.Default
    private Set<PersonEntity> participants = new HashSet<>();

    private LocalDate marriageDate;
    private LocalDate divorceDate;
    private String marriagePlace;
}
