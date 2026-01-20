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
public class Marriage {

    private UUID uuid;

    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    private Set<Person> participants = new HashSet<>();

    private LocalDate marriageDate;
    private LocalDate divorceDate;
    private String marriagePlace;

    public void addParticipant(Person person) {
        if (participants.size() >= 2) {
            throw new IllegalArgumentException("A marriage cannot have more than 2 participants");
        }
        this.participants.add(person);
    }

    public Person getSpouseOf(Person person) {
        return participants.stream()
                .filter(p -> !p.equals(person))
                .findFirst()
                .orElse(null);
    }
}
