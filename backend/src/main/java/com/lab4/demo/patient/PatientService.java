package com.lab4.demo.patient;

import com.lab4.demo.patient.model.Patient;
import com.lab4.demo.patient.model.PatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    private Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + id));
    }


    public List<PatientDTO> findAll() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto).collect(toList());
    }

    public PatientDTO edit(Long id, PatientDTO patientDTO) {
        Patient patient = findById(id);
        patient.setName(patientDTO.getName());
        patient.setAddress(patientDTO.getAddress());
        patient.setIdentityCard(patientDTO.getIdentityCard());

        return patientMapper.toDto(
                patientRepository.save(patient));
    }

    public void delete(Long id) {
        patientRepository.deleteById((id));

    }

    public PatientDTO create(PatientDTO patientDTO) {
        return patientMapper.toDto(patientRepository
                .save(patientMapper.fromDto(patientDTO)));
    }
}
