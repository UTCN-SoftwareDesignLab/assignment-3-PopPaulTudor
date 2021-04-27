package com.lab4.demo.patient;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.patient.model.PatientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.UrlMapping.ENTITY;
import static com.lab4.demo.UrlMapping.PATIENTS;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatientControllerTest extends BaseControllerTest {

    @InjectMocks
    private PatientController controller;

    @Mock
    private PatientService patientService;


    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new PatientController(patientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findAllPatients() throws Exception {

        List<PatientDTO> patientDTOS = TestCreationFactory.listOf(PatientDTO.class);
        when(patientService.findAll()).thenReturn(patientDTOS);

        ResultActions response = mockMvc.perform(get(PATIENTS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patientDTOS));
    }

    @Test
    void create() throws Exception {
        PatientDTO patientDTO = TestCreationFactory.object(PatientDTO.class);

        when(patientService.create(patientDTO)).thenReturn(patientDTO);

        ResultActions result = performPostWithRequestBody(PATIENTS, patientDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patientDTO));
    }

    @Test
    void edit() throws Exception {
        PatientDTO patientDTO = TestCreationFactory.object(PatientDTO.class);
        long id = patientDTO.getId();

        when(patientService.edit(id, patientDTO)).thenReturn(patientDTO);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(PATIENTS + ENTITY, patientDTO, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patientDTO));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        doNothing().when(patientService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(PATIENTS + ENTITY, id);
        verify(patientService, times(1)).delete(id);

        result.andExpect(status().isOk());
    }
}