package com.lab4.demo.patient.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long identityCard;

    @Column(nullable = false)
    private Long cnp;

    @Column(nullable = false)
    private Long birthDate;

    @Column(nullable = false)
    private String address;
}
