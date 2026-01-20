package com.geneai.infrastructure.adapters.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String birthPlace;
    private LocalDate deathDate;
    private String deathPlace;
    
    private UUID fatherUuid;
    private UUID motherUuid;
    
    private List<UUID> childrenUuids;
    private List<MarriageDTO> marriages;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MarriageDTO {
        private UUID spouseUuid;
        private LocalDate marriageDate;
        private String marriagePlace;
        private LocalDate divorceDate;
    }
}
