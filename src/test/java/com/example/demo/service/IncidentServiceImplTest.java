package com.example.demo.service;

import com.example.demo.dao.IncidentDao;
import com.example.demo.model.Incident;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IncidentServiceImplTest {

    @Mock
    private IncidentDao incidentDao;

    @InjectMocks
    private IncidentServiceImpl incidentService;

    @Test
    public void testCreateIncident() {
        Incident incident = new Incident();
        incident.setDescription("message");
        when(incidentDao.save(incident)).thenReturn(incident);

        Incident createdIncident = incidentService.createIncident(incident);

        assertNotNull(createdIncident);
        verify(incidentDao, times(1)).save(incident);
    }

    @Test
    public void testDeleteIncident() {
        Long id = 1L;

        incidentService.deleteIncident(id);

        verify(incidentDao, times(1)).deleteById(id);
    }

    @Test
    public void testModifyIncident() {
        Long id = 1L;
        Incident newIncident = new Incident();
        newIncident.setDescription("message");

        Incident existingIncident = new Incident();
        existingIncident.setId(id);
        when(incidentDao.findById(id)).thenReturn(existingIncident);
        when(incidentDao.update(any(Incident.class))).thenReturn(newIncident);
        Incident modifiedIncident = incidentService.modifyIncident(id, newIncident);

        assertNotNull(modifiedIncident);
        verify(incidentDao, times(1)).update(newIncident);
    }

    @Test
    public void testModifyIncidentNotFound() {
        Long id = 1L;
        Incident newIncident = new Incident();
        newIncident.setDescription("message");

        when(incidentDao.findById(id)).thenReturn(null);

        Incident modifiedIncident = incidentService.modifyIncident(id, newIncident);

        assertNull(modifiedIncident);
    }

    @Test
    public void testListAllIncidents() {
        List<Incident> incidents = new ArrayList<>();
        incidents.add(new Incident());
        when(incidentDao.findAll()).thenReturn(incidents);

        List<Incident> allIncidents = incidentService.listAllIncidents();

        assertNotNull(allIncidents);
        assertEquals(1, allIncidents.size());
        verify(incidentDao, times(1)).findAll();
    }

    @Test
    public void testListIncidentsByPage() {
        List<Incident> expectedIncidents = new ArrayList<>();
        expectedIncidents.add(new Incident());
        Mockito.when(incidentDao.findAllByPage(1, 10)).thenReturn(expectedIncidents);

        // 调用 listIncidentsByPage 方法
        List<Incident> actualIncidents = incidentService.listIncidentsByPage(1, 10);

        // 验证结果
        assertEquals(expectedIncidents, actualIncidents);
    }
}
