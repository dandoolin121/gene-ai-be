package com.geneai.model;

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
public class Marriage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Builder.Default
    private UUID uuid = UUID.randomUUID();

    @ManyToMany
    @JoinTable(
        name = "person_marriages",
        joinColumns = @JoinColumn(name = "marriage_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    @Builder.Default
    private Set<Person> participants = new HashSet<>();

    private LocalDate marriageDate;
    private LocalDate divorceDate;
    private String marriagePlace;

    public Person getSpouseOf(Person person) {
        return participants.stream()
                .filter(p -> !p.equals(person))
                .findFirst()
                .orElse(null);
    }
}
