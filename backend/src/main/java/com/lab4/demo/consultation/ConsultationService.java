package com.lab4.demo.consultation;


import com.lab4.demo.consultation.model.Consultation;
import com.lab4.demo.consultation.model.dto.ConsultationDTO;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import com.lab4.demo.websocket.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static com.lab4.demo.UrlMapping.CHAT;
import static com.lab4.demo.UrlMapping.ENTITY;
import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;
    private final UserRepository userRepository;


    private Consultation findById(Long id) {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found: " + id));
    }


    public List<ConsultationDTO> findAll() {
        return consultationRepository.findAll().stream()
                .map(consultationMapper::toDto).collect(toList());
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


    @Scheduled(fixedRate = 3000)
    public void sendMessage(SimpMessagingTemplate simpMessagingTemplate) {
        long oneHour = 3600000L;
        long time;
        List<Consultation> consultations;

        Role role = Role.builder().name(ERole.DOCTOR).build();
        List<User> doctors = userRepository.findAllByRolesContaining(role);

        for (User doctor : doctors) {
            time = Calendar.getInstance().getTimeInMillis() - oneHour;
            consultations = consultationRepository.findByDoctorIdAndTimeOfConsultation(doctor.getId(), time);

            for (Consultation consultation : consultations) {
                Message message = Message.builder()
                        .title("You have an upcoming consultation with " + consultation.getId())
                        .text(consultation.getDetails())
                        .build();

                simpMessagingTemplate.convertAndSend(CHAT + ENTITY, message);


            }

        }

    }
}
