package com.lab4.demo.consultation;


import com.lab4.demo.consultation.model.Consultation;
import com.lab4.demo.consultation.model.dto.ConsultationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;


    private Consultation findById(Long id) {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found: " + id));
    }

    private List<Consultation> findByDoctorId(Long doctorId) {
        List<Consultation> consultations = consultationRepository.findByDoctorId(doctorId);

        return consultations;
    }

    public List<ConsultationDTO> findAll() {
        return consultationRepository.findAll().stream()
                .map(consultationMapper::toDto).collect(toList());
    }


    private List<Consultation> findByPatientId(Long doctorId) {

        return consultationRepository.findByPatientId(doctorId);
    }

    public Optional<ConsultationDTO> create(ConsultationDTO consultationDTO) {

        List<Consultation> consultations = consultationRepository
                .findByDoctorIdAndTimeOfConsultation(consultationDTO.getDoctorId(), consultationDTO.getTimeOfConsultation());

        if (consultations.isEmpty())
            return Optional.of(consultationMapper.toDto(consultationRepository.save(consultationMapper.fromDto(consultationDTO))));
        else
            return Optional.empty();
    }

    public ConsultationDTO edit(Long id, ConsultationDTO consultationDTO) {
        Consultation consultation = findById(id);
        consultation.setDoctorId(consultationDTO.getDoctorId());
        consultation.setTimeOfConsultation(consultationDTO.getTimeOfConsultation());
        consultation.setPatientId(consultationDTO.getPatientId());

        return consultationMapper.toDto(
                consultationRepository.save(consultation));

    }

    public void delete(Long id) {
        consultationRepository.deleteById((id));

    }
}
