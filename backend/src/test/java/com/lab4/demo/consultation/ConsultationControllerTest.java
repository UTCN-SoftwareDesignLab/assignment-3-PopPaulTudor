package com.lab4.demo.consultation;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.consultation.model.dto.ConsultationDTO;
import com.lab4.demo.patient.model.PatientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ConsultationControllerTest extends BaseControllerTest {


    @InjectMocks ConsultationController controller;

    @Mock
    private ConsultationService consultationService;


    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new ConsultationController(consultationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findAllConsultations() throws Exception {

        List<ConsultationDTO> dtos = TestCreationFactory.listOf(ConsultationDTO.class);
        when(consultationService.findAll()).thenReturn(dtos);

        ResultActions response = mockMvc.perform(get(CONSULTATIONS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(dtos));
    }

    @Test
    void create() throws Exception {
        ConsultationDTO consultationDTO = TestCreationFactory.object(ConsultationDTO.class);

        when(consultationService.create(consultationDTO)).thenReturn(Optional.of(consultationDTO));

        ResultActions result = performPostWithRequestBody(CONSULTATIONS, consultationDTO);
        result.andExpect(status().isOk());
    }

    @Test
    void edit() throws Exception {
        ConsultationDTO consultationDTO = TestCreationFactory.object(ConsultationDTO.class);
        long id = consultationDTO.getId();

        when(consultationService.edit(id, consultationDTO)).thenReturn(consultationDTO);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(CONSULTATIONS + ENTITY, consultationDTO, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDTO));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        doNothing().when(consultationService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(CONSULTATIONS + ENTITY, id);
        verify(consultationService, times(1)).delete(id);

        result.andExpect(status().isOk());
    }
}