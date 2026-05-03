package com.portfolio.bookingapp.controllers;

import com.portfolio.bookingapp.services.impl.MailSenderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VenueControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private MailSenderService emailService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createVenueTest() throws Exception {
        String jsonRequest = "{\"name\":\"Вднг\",\"address\":\"ст. м. Виставковий центр\"}";

        var response = mockMvc.perform(
                post("/api/venues").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
        ).andReturn();

        String responseBody = response.getResponse().getContentAsString();

        assertEquals("{\"id\":1,\"name\":\"Вднг\",\"address\":\"ст. м. Виставковий центр\"}", responseBody);
    }
}
