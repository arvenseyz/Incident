package com.example.demo.controller;

import com.example.demo.model.Incident;
import com.example.demo.service.IncidentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/incidents")
@Validated
public class IncidentController {
    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createIncident(@RequestBody Incident incident) {
        try {
            Incident created = incidentService.createIncident(incident);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("data", created);
            responseMap.put("status", HttpStatus.CREATED.value());
            return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("errorMessage", e.getMessage());
            errorMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteIncident(@PathVariable Long id) {
        try {
            incidentService.deleteIncident(id);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("status", HttpStatus.NO_CONTENT.value());
            return new ResponseEntity<>(responseMap, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("errorMessage", e.getMessage());
            errorMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> modifyIncident(@PathVariable Long id, @RequestBody Incident newIncident) {
        try {
            Incident modified = incidentService.modifyIncident(id, newIncident);
            if (modified!= null) {
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("data", modified);
                responseMap.put("status", HttpStatus.OK.value());
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            } else {
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("status", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(responseMap, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("errorMessage", e.getMessage());
            errorMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listAllIncidents() {
        try {
            List<Incident> incidents = incidentService.listAllIncidents();
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("data", incidents);
            responseMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("errorMessage", e.getMessage());
            errorMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 分页查询接口
    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> listAllIncidentsByPage(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<Incident> incidents = incidentService.listIncidentsByPage(pageNumber, pageSize);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("data", incidents);
            responseMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("errorMessage", e.getMessage());
            errorMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}