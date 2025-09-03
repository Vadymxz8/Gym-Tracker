package com.vadim.tkach.gym_tracker.progress;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:test-schema.sql")
@Sql(scripts = "classpath:test-data.sql")
class SmokeEndpointsTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void workoutsEndpoint() throws Exception {
        mockMvc.perform(get("/api/workouts"))
                .andExpect(status().isOk());
    }

    @Test
    void exercisesEndpoint() throws Exception {
        mockMvc.perform(get("/api/exercises"))
                .andExpect(status().isOk());
    }
}
