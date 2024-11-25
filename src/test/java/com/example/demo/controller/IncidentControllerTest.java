package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.model.Incident;
import com.example.demo.service.IncidentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebMvcTest(IncidentController.class)
class IncidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncidentService incidentService;

    @Test
    void createIncident_Success() throws Exception {
        Incident incident = new Incident();
        when(incidentService.createIncident(incident)).thenReturn(incident);
        RequestBuilder request = MockMvcRequestBuilders.post("/incidents")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");

        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    @Test
    void createIncident_Failure() throws Exception {
        Incident incident = new Incident();
        when(incidentService.createIncident(incident)).thenThrow(new RuntimeException("create incident failed"));
        RequestBuilder request = MockMvcRequestBuilders.post("/incidents")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");


        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void deleteIncident_Success() throws Exception {
        doNothing().when(incidentService).deleteIncident(1L);
        RequestBuilder request = MockMvcRequestBuilders.delete("/incidents/1");
        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    void deleteIncident_Failure() throws Exception {
        doThrow(new RuntimeException("delete incident failed")).when(incidentService).deleteIncident(1L);
        RequestBuilder request = MockMvcRequestBuilders.delete("/incidents/1");
        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void modifyIncident_Success() throws Exception {
        Incident newIncident = new Incident();
        when(incidentService.modifyIncident(1L, newIncident)).thenReturn(newIncident);
        RequestBuilder request = MockMvcRequestBuilders.put("/incidents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");
        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void modifyIncident_NotFound() throws Exception {
        Incident newIncident = new Incident();
        when(incidentService.modifyIncident(1L, newIncident)).thenReturn(null);
        RequestBuilder request = MockMvcRequestBuilders.put("/incidents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");
        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    void modifyIncident_Failure() throws Exception {
        Incident newIncident = new Incident();
        when(incidentService.modifyIncident(1L, newIncident)).thenThrow(new RuntimeException("modify incident failed"));
        RequestBuilder request = MockMvcRequestBuilders.put("/incidents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");

        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void listAllIncidents_Success() throws Exception {
        List<Incident> incidents = new ArrayList<>();
        incidents.add(new Incident());
        when(incidentService.listAllIncidents()).thenReturn(incidents);

        RequestBuilder request = MockMvcRequestBuilders.get("/incidents");
        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void listAllIncidents_Failure() throws Exception {
        when(incidentService.listAllIncidents()).thenThrow(new RuntimeException("list incident failed"));
        RequestBuilder request = MockMvcRequestBuilders.get("/incidents");
        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void listAllIncidentsByPage_Success() throws Exception {
        int pageNumber = 1;
        int pageSize = 10;
        List<Incident> incidents = new ArrayList<>();
        incidents.add(new Incident());
        when(incidentService.listIncidentsByPage(pageNumber, pageSize)).thenReturn(incidents);
        RequestBuilder request = MockMvcRequestBuilders.get("/incidents/page")
                .param("pageNumber", String.valueOf(pageNumber))
                .param("pageSize", String.valueOf(pageSize));
        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void listAllIncidentsByPage_Failure() throws Exception {
        int pageNumber = 1;
        int pageSize = 10;
        when(incidentService.listIncidentsByPage(pageNumber, pageSize)).thenThrow(new RuntimeException("page incident failed"));

        RequestBuilder request = MockMvcRequestBuilders.get("/incidents/page")
                .param("pageNumber", String.valueOf(pageNumber))
                .param("pageSize", String.valueOf(pageSize));

        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    private Map<String, Object> parseResponse(String responseContent) {
        return new HashMap<>();
    }
}
