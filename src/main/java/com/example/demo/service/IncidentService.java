package com.example.demo.service;

import com.example.demo.model.Incident;
import java.util.List;

public interface IncidentService {
    Incident createIncident(Incident incident);
    void deleteIncident(Long id);
    Incident modifyIncident(Long id, Incident newIncident);
    List<Incident> listAllIncidents();
    // 添加分页查询服务方法
    List<Incident> listIncidentsByPage(int pageNumber, int pageSize);
}