package com.example.demo.dao;

import com.example.demo.model.Incident;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class IncidentDaoImpl implements IncidentDao {
    private final List<Incident> incidents = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();
    private final HashMap<Long,Incident> index = new HashMap<>();

    @Override
    public Incident save(Incident incident) {
        incident.setId(counter.incrementAndGet());
        incidents.add(incident);
        index.put(incident.getId(),incident);
        return incident;
    }

    @Override
    public void deleteById(Long id) {
        incidents.removeIf(incident -> incident.getId().equals(id));
        index.remove(id);
    }

    @Override
    public Incident update(Incident incident) {
        for (int i = 0; i < incidents.size(); i++) {
            Incident current = incidents.get(i);
            if (current.getId().equals(incident.getId())) {
                incidents.set(i, incident);
                return incident;
            }
        }
        index.put(incident.getId(),incident);
        return null;
    }

    @Override
    public Incident findById(Long id) {
        if (index.containsKey(id)) {
            return index.get(id);
        }
        for (Incident incident : incidents) {
            if (incident.getId().equals(id)) {
                return incident;
            }
        }
        return null;
    }

    @Override
    public List<Incident> findAll() {
        return new ArrayList<>(incidents);
    }

    @Override
    public List<Incident> findAllByPage(int pageNumber, int pageSize) {
        int size = incidents.size();
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, incidents.size());
        if (endIndex < startIndex||endIndex > size||startIndex<0||startIndex>size) {
            throw new IllegalArgumentException("Invalid page number or pageSize");
        }
        return incidents.subList(startIndex, endIndex);
    }
}