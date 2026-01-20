package com.geneai.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    private String firstName;
    private String lastName;

    private LocalDate birthDate;
    private String residence;

    private LocalDate deathDate;
    private String burialPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "father_id")
    private Person father;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mother_id")
    private Person mother;

    @OneToMany(mappedBy = "father")
    @Builder.Default
    private Set<Person> childrenFromFather = new HashSet<>();

    @OneToMany(mappedBy = "mother")
    @Builder.Default
    private Set<Person> childrenFromMother = new HashSet<>();

    @OneToMany(mappedBy = "person1", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Marriage> marriagesAsPerson1 = new HashSet<>();

    @OneToMany(mappedBy = "person2", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Marriage> marriagesAsPerson2 = new HashSet<>();

    public Set<Person> getChildren() {
        Set<Person> children = new HashSet<>(childrenFromFather);
        children.addAll(childrenFromMother);
        return children;
    }

    public Set<Marriage> getMarriages() {
        Set<Marriage> marriages = new HashSet<>(marriagesAsPerson1);
        marriages.addAll(marriagesAsPerson2);
        return marriages;
    }
}
