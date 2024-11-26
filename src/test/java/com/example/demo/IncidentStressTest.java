package com.example.demo;

import com.example.demo.model.Incident;
import com.example.demo.service.IncidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@WebMvcTest
public class IncidentStressTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncidentService incidentService;

    private final List<Incident> mockIncidents = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        // 设置一些模拟数据
        for (int i = 0; i < 100; i++) {
            Incident incident = new Incident();
            incident.setId((long) i);
            incident.setDescription("Mock incident " + i);
            mockIncidents.add(incident);
        }
        Long id = 1L;
        Incident newIncident = new Incident();
        newIncident.setDescription("Modified incident");
        when(incidentService.listAllIncidents()).thenReturn(mockIncidents);
        when(incidentService.modifyIncident(anyLong(), any(Incident.class))).thenReturn(newIncident);
    }

    @Test
    public void testCreateIncidentUnderMultiThreadStress() throws Exception {
        int numThreads = 50; // 设置并发线程数
        int numRequestsPerThread = 20; // 每个线程执行的请求次数

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            futures.add(executorService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (int j = 0; j < numRequestsPerThread; j++) {
                        Incident incident = new Incident();
                        incident.setDescription("Multi-thread stress test incident " + j);

                        String json = "{\"description\":\"" + incident.getDescription() + "\"}";

                        mockMvc.perform(MockMvcRequestBuilders.post("/incidents")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(json))
                                .andExpect(status().isCreated());
                    }
                    return null;
                }
            }));
        }

        for (Future<?> future : futures) {
            future.get();
        }

        verify(incidentService, times(numThreads * numRequestsPerThread)).createIncident(any(Incident.class));

        executorService.shutdown();
    }

    @Test
    public void testDeleteIncidentUnderMultiThreadStress() throws Exception {
        int numThreads = 50;
        int numRequestsPerThread = 20;

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            futures.add(executorService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (int j = 0; j < numRequestsPerThread; j++) {
                        Incident incident = mockIncidents.get(j % mockIncidents.size());

                        mockMvc.perform(MockMvcRequestBuilders.delete("/incidents/" + incident.getId()))
                                .andExpect(status().isNoContent());
                    }
                    return null;
                }
            }));
        }

        for (Future<?> future : futures) {
            future.get();
        }

        verify(incidentService, times(numThreads * numRequestsPerThread)).deleteIncident(anyLong());

        executorService.shutdown();
    }

    @Test
    public void testModifyIncidentUnderMultiThreadStress() throws Exception {
        int numThreads = 50;
        int numRequestsPerThread = 20;

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            futures.add(executorService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (int j = 0; j < numRequestsPerThread; j++) {
                        Incident incident = mockIncidents.get(j % mockIncidents.size());
                        Incident newIncident = new Incident();
                        newIncident.setId(incident.getId());
                        newIncident.setDescription("Multi-thread modified stress test incident " + j);

                        String json = "{\"description\":\"" + newIncident.getDescription() + "\"}";

                        mockMvc.perform(MockMvcRequestBuilders.put("/incidents/" + newIncident.getId())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(json))
                                .andExpect(status().isOk());
                    }
                    return null;
                }
            }));
        }

        for (Future<?> future : futures) {
            future.get();
        }

        verify(incidentService, times(numThreads * numRequestsPerThread)).modifyIncident(anyLong(), any(Incident.class));

        executorService.shutdown();
    }

    @Test
    public void testListAllIncidentsUnderMultiThreadStress() throws Exception {
        int numThreads = 50;
        int numRequestsPerThread = 20;

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            futures.add(executorService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for (int j = 0; j < numRequestsPerThread; j++) {
                        mockMvc.perform(MockMvcRequestBuilders.get("/incidents"))
                                .andExpect(status().isOk());
                    }
                    return null;
                }
            }));
        }

        for (Future<?> future : futures) {
            future.get();
        }

        verify(incidentService, times(numThreads * numRequestsPerThread)).listAllIncidents();

        executorService.shutdown();
    }
}