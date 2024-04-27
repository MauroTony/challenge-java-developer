package br.com.neurotech.challenge.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFluxoClient() throws Exception {
        String clientJson = "{\"name\":\"Test Client 3\",\"age\":30,\"income\":50000}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated());

        String clientId = "1";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/" + clientId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clients/" + clientId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetClientNotFound() throws Exception {
        String clientId = "1";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/" + clientId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteClient() throws Exception {
        String clientId = "2";
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clients/" + clientId))
                .andExpect(status().isNotFound());
    }


}