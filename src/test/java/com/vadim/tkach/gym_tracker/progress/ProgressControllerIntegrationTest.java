package com.vadim.tkach.gym_tracker.progress;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:test-schema.sql")
@Sql(scripts = "classpath:test-data.sql")
class ProgressControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    private static final String USER = "00000000-0000-0000-0000-000000000001";
    private static final String EXERCISE = "00000000-0000-0000-0000-000000000101";

    @Test
    void summaryEndpoint() throws Exception {
        mockMvc.perform(get("/api/progress/summary")
                        .param("userId", USER)
                        .param("range", "week"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totals.volume").value(4800.0))
                .andExpect(jsonPath("$.totals.sessions").value(2));
    }

    @Test
    void exerciseEndpoint() throws Exception {
        LocalDate d1 = LocalDate.now().minusDays(2);
        mockMvc.perform(get("/api/progress/exercise")
                        .param("userId", USER)
                        .param("exerciseId", EXERCISE)
                        .param("range", "week"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].date").value(d1.toString()))
                .andExpect(jsonPath("$[0].volume").value(3000.0));
    }

    @Test
    void sessionsEndpoint() throws Exception {
        mockMvc.perform(get("/api/sessions")
                        .param("userId", USER)
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("W2"))
                .andExpect(jsonPath("$[1].name").value("W1"));
    }
}
