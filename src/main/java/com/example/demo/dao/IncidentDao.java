package com.example.demo.dao;

import com.example.demo.model.Incident;
import java.util.List;

public interface IncidentDao {
    Incident save(Incident incident);
    void deleteById(Long id);
    Incident update(Incident incident);
    Incident findById(Long id);
    List<Incident> findAll();
    List<Incident> findAllByPage(int pageNumber, int pageSize);
}