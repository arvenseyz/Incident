package com.example.demo.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dao.IncidentDao;
import com.example.demo.model.Incident;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class IncidentDaoImplTest {

    @Autowired
    private IncidentDao incidentDao;

    @Test
    public void testSave() {
        Incident incident = new Incident();
        incident.setDescription("Test incident");

        Incident savedIncident = incidentDao.save(incident);

        Assertions.assertNotNull(savedIncident);
        Assertions.assertNotNull(savedIncident.getId());
    }

    @Test
    public void testDeleteById() {
        Incident incident = new Incident();
        incident.setDescription("Test incident");

        Incident savedIncident = incidentDao.save(incident);

        incidentDao.deleteById(savedIncident.getId());

        Incident foundIncident = incidentDao.findById(savedIncident.getId());

        Assertions.assertNull(foundIncident);
    }

    @Test
    public void testUpdate() {
        Incident incident = new Incident();
        incident.setDescription("Test incident");

        Incident savedIncident = incidentDao.save(incident);

        savedIncident.setDescription("Updated description");

        Incident updatedIncident = incidentDao.update(savedIncident);

        Assertions.assertEquals("Updated description", updatedIncident.getDescription());
    }

    @Test
    public void testFindById() {
        Incident incident = new Incident();
        incident.setDescription("Test incident");

        Incident savedIncident = incidentDao.save(incident);

        Incident foundIncident = incidentDao.findById(savedIncident.getId());

        Assertions.assertNotNull(foundIncident);
        Assertions.assertEquals(savedIncident.getId(), foundIncident.getId());
    }

    @Test
    public void testFindAll() {
        Incident incident1 = new Incident();
        incident1.setDescription("Test incident 1");

        Incident incident2 = new Incident();
        incident2.setDescription("Test incident 2");

        incidentDao.save(incident1);
        incidentDao.save(incident2);

        List<Incident> allIncidents = incidentDao.findAll();

        Assertions.assertEquals(2, allIncidents.size());
    }

    @Test
    public void testFindAllByPage() {
        for (int i = 1; i <= 10; i++) {
            incidentDao.save(new Incident((long) i,""));
        }

        // first page
        List<Incident> page1 = incidentDao.findAllByPage(1, 5);
        Assertions.assertEquals(5, page1.size());

        // second page
        List<Incident> page2 = incidentDao.findAllByPage(2, 5);
        Assertions.assertEquals(5, page2.size());

        // last page
        List<Incident> lastPage = incidentDao.findAllByPage(3, 5);
        Assertions.assertEquals(0, lastPage.size());

        // boundary case
        List<Incident> boundaryPage = incidentDao.findAllByPage(1, 10);
        Assertions.assertEquals(10, boundaryPage.size());

        // unused page
        Assertions.assertThrows(IllegalArgumentException.class, () -> incidentDao.findAllByPage(0, 5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> incidentDao.findAllByPage(-1, 5));

        // unused page size
        Assertions.assertThrows(IllegalArgumentException.class, () -> incidentDao.findAllByPage(1, 0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> incidentDao.findAllByPage(1, -1));
    }
}
