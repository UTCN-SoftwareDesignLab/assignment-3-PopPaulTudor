package com.lab4.demo.consultation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDTO {

    @Builder.Default
    private Long id = -1L;

    @Builder.Default
    private Long patientId = -1L;

    @Builder.Default
    private Long doctorId = -1L;

    @Builder.Default
    private Long timeOfConsultation = -1L;

    @Builder.Default
    private String details = "";

}
