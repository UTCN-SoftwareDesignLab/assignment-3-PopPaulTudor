package com.lab4.demo.patient;


import com.lab4.demo.patient.model.PatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(PATIENTS)
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;


    @GetMapping
    public List<PatientDTO> findAllPatients(){
        return patientService.findAll();}

    @PostMapping
    public PatientDTO create(@RequestBody PatientDTO patient) {
        return patientService.create(patient);
    }

    @PatchMapping(ENTITY)
    public PatientDTO edit(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        return patientService.edit(id, patientDTO);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        patientService.delete(id);
    }


}
