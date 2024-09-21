package hr.algebra.healthyapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hr.algebra.healthyapp.dto.AppointmentDto;
import hr.algebra.healthyapp.dto.UserDto;
import hr.algebra.healthyapp.mapper.AppointmentMapper;
import hr.algebra.healthyapp.service.AppointmentService;
import hr.algebra.healthyapp.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentController appointmentController;

    private MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private AppointmentDto appointmentDto;

    @BeforeEach
    public void beforeAll() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();

        UserDto doctor = UserDto.builder()
                .name("Doctor")
                .email("doctor@example.com")
                .role(Role.ADMIN)
                .build();
        UserDto patient = UserDto.builder()
                .name("Doe")
                .email("john@example.com")
                .role(Role.USER)
                .build();
        appointmentDto = AppointmentDto.builder()
                .endDateTime(LocalDateTime.now())
                .startDateTime(LocalDateTime.now())
                .doctor(doctor)
                .patient(patient)
                .address("Address").build();
    }

    @Test
    void testGetAppointmentsByDoctor() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");
        when(appointmentService.getAppointmentsByUser(anyString())).thenReturn(Collections.emptyList());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/appointment")
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(appointmentService, times(1)).getAppointmentsByUser(anyString());
    }

    @Test
    void testCreateAppointment() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/appointment")
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(appointmentDto))
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(appointmentService, times(1)).saveAppointment(any(), anyString());
    }

    @Test
    void testUpdateAppointment() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/appointment")
                .contentType(APPLICATION_JSON_UTF8)
                .content(asJsonString(appointmentDto))
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(appointmentService, times(1)).saveAppointment(any(), anyString());
    }

    @Test
    void testDeleteAppointment() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("me");
        Long appointmentId = 1L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/appointment/{appointmentId}", appointmentId)
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(appointmentService, times(1)).deleteAppointment(appointmentId);
    }

    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            objectMapper.registerModule(javaTimeModule);
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
