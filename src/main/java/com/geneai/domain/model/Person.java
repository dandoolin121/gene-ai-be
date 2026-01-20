package com.geneai.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    private UUID uuid;

    private String firstName;
    private String lastName;

    private LocalDate birthDate;
    private String residence;

    private LocalDate deathDate;
    private String burialPlace;

    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    private Set<Person> parents = new HashSet<>();

    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    private Set<Person> children = new HashSet<>();

    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    private Set<Marriage> marriages = new HashSet<>();

    public void addParent(Person parent) {
        validateParentLimit();
        this.parents.add(parent);
        parent.children.add(this);
    }

    private void validateParentLimit() {
        if (this.parents.size() >= 2) {
            throw new IllegalArgumentException("A person cannot have more than 2 parents");
        }
    }
}
