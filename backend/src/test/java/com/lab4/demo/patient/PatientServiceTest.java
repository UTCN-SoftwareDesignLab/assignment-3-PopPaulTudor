package com.lab4.demo.patient;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.patient.model.Patient;
import com.lab4.demo.patient.model.PatientDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.when;

class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientService = new PatientService(patientRepository, patientMapper);
    }

    @Test
    void findAll() {
        List<Patient> patients = TestCreationFactory.listOf(Patient.class);
        when(patientRepository.findAll()).thenReturn(patients);

        List<PatientDTO> all = patientService.findAll();

        Assertions.assertEquals(patients.size(), all.size());


    }

    @Test
    void create() {
        PatientDTO patientDTO = TestCreationFactory.object(PatientDTO.class);
        Patient patient = patientMapper.fromDto(patientDTO);

        when(patientMapper.fromDto(patientDTO)).thenReturn(patient);
        when(patientMapper.toDto(patient)).thenReturn(patientDTO);
        when(patientRepository.save(patient)).thenReturn(patient);

        Assertions.assertEquals(patientDTO.getId(), patientService.create(patientDTO).getId());
    }


}