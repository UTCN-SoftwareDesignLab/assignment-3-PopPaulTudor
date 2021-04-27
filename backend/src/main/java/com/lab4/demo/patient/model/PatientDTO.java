package com.lab4.demo.patient.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    @Builder.Default
    private Long id = -1L;

    @Builder.Default
    private String name = "";

    @Builder.Default
    private Long identityCard = -1L;

    @Builder.Default
    private Long cnp = -1L;

    @Builder.Default
    private Long birthDate = -1L;

    @Builder.Default
    private String address = "";
}