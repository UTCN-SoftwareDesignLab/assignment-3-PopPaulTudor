package com.lab4.demo.consultation;


import com.lab4.demo.consultation.model.dto.ConsultationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.lab4.demo.UrlMapping.CONSULTATIONS;
import static com.lab4.demo.UrlMapping.ENTITY;

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
        if (optional.isPresent())
            return "Schedule created";
        else
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


}
