package com.lab4.demo.consultation;


import com.lab4.demo.consultation.model.Consultation;
import com.lab4.demo.consultation.model.dto.ConsultationDTO;
import com.lab4.demo.websocket.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(CONSULTATIONS)
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @GetMapping
    public List<ConsultationDTO> findAllPatients() {
        return consultationService.findAll();
    }


    @PostMapping
    public String create(@RequestBody ConsultationDTO consultationDTO) {
        Optional<ConsultationDTO> optional = consultationService.create(consultationDTO);
        if (optional.isPresent()) {
            ConsultationDTO dto = optional.get();
            Message message = Message.builder()
                    .title("New consultation scheduled")
                    .text("You have a new consultation at " + dto.getTimeOfConsultation() +
                    " with " + dto.getPatientId()).build();
            announceSchedule(consultationDTO.getDoctorId(), message);
            return "Schedule created";

        }else
            return "There is already a schedule in doctors program";
    }

    @PatchMapping(ENTITY)
    public ConsultationDTO edit(@PathVariable Long id, @RequestBody ConsultationDTO consultationDTO) {
        return consultationService.edit(id, consultationDTO);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        consultationService.delete(id);
    }

    @MessageMapping()
    @SendTo(CHAT + ENTITY)
    public Message announceSchedule(@PathVariable Long id, @RequestBody Message message){

        return message;
    }

}
