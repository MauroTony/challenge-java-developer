package br.com.neurotech.challenge.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CreditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        String clientJson = "{\"name\":\"Test Client 1\",\"age\":21,\"income\":2000}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated());
        clientJson = "{\"name\":\"Test Client 2\",\"age\":25,\"income\":6000}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated());
        clientJson = "{\"name\":\"Test Client 3\",\"age\":70,\"income\":1000}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated());
        clientJson = "{\"name\":\"Test Client 4\",\"age\":30,\"income\":1000}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated());
        clientJson = "{\"name\":\"Test Client 5\",\"age\":24,\"income\":5000}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated());
        clientJson = "{\"name\":\"Test Client 6\",\"age\":24,\"income\":5000}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCheckCredit() throws Exception {
        String clientId = "1";
        String vehicleModel = "HATCH";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/credit/check/" + clientId + "/" + vehicleModel)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetCreditTypeFixed() throws Exception {
        String clientId = "1";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/credit/type/" + clientId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"creditOption\":\"FIXED\"}"));
    }

    @Test
    public void testGetCreditTypeVariable() throws Exception {
        String clientId = "2";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/credit/type/" + clientId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"creditOption\":\"VARIABLE\"}"));
    }

    @Test
    public void testGetCreditTypeConsigned() throws Exception {
        String clientId = "3";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/credit/type/" + clientId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"creditOption\":\"CONSIGNED\"}"));
    }

    @Test
    public void testGetClientProfile() throws Exception {
        String vehicleModel = "HATCH";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/credit/credit-fixed/clients-profile/" + vehicleModel)
                        .param("minAge", "23")
                        .param("maxAge", "49")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"name\":\"Test Client 5\",\"age\":24,\"income\":5000}, {\"name\":\"Test Client 6\",\"age\":24,\"income\":5000}]"));

    }
}