package com.example.demo.service;

import com.example.demo.dao.IncidentDao;
import com.example.demo.model.Incident;
import java.util.List;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class IncidentServiceImpl implements IncidentService {
    private final IncidentDao incidentDao;

    public IncidentServiceImpl(IncidentDao incidentDao) {
        this.incidentDao = incidentDao;
    }


    @Cacheable("incidents")
    @Override
    public Incident createIncident(Incident incident) {
        // 参数校验
        if (!incident.isValid()) {
            throw new IllegalArgumentException("message cannot be empty");
        }

        try {
            return incidentDao.save(incident);
        } catch (Exception e) {
            throw new RuntimeException("create incident fail:" + e.getMessage());
        }
    }

    @CacheEvict("incidents")
    @Override
    public void deleteIncident(Long id) {
        try {
            incidentDao.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("delete incident fail:" + e.getMessage());
        }
    }

    @CachePut("incidents")
    @Override
    public Incident modifyIncident(Long id, Incident newIncident) {
        // 参数校验
        if (!newIncident.isValid()) {
            throw new IllegalArgumentException("message cannot be empty");
        }

        Incident existing = incidentDao.findById(id);
        if (existing!= null) {
            newIncident.setId(id);
            try {
                return incidentDao.update(newIncident);
            } catch (Exception e) {
                throw new RuntimeException("modify incident fail:" + e.getMessage());
            }
        }
        return null;
    }

    @Cacheable("incidents")
    @Override
    public List<Incident> listAllIncidents() {
        try {
            return incidentDao.findAll();
        } catch (Exception e) {
            throw new RuntimeException("list incident fail:" + e.getMessage());
        }
    }

    @Cacheable("incidents")
    @Override
    public List<Incident> listIncidentsByPage(int pageNumber, int pageSize) {
        try {
            return incidentDao.findAllByPage(pageNumber, pageSize);
        } catch (Exception e) {
            throw new RuntimeException("page incident fail:" + e.getMessage());
        }
    }
}