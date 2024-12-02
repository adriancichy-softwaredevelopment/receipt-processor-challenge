package com.adriancichy.receiptprocessor.application.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReceiptControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testProcessReceiptAndGetPoints_ExampleOne() throws Exception {
        // Example 1 Input from the original README.md
        String receiptJson = """
        {
          "retailer": "Target",
          "purchaseDate": "2022-01-01",
          "purchaseTime": "13:01",
          "items": [
            {
              "shortDescription": "Mountain Dew 12PK",
              "price": "6.49"
            },{
              "shortDescription": "Emils Cheese Pizza",
              "price": "12.25"
            },{
              "shortDescription": "Knorr Creamy Chicken",
              "price": "1.26"
            },{
              "shortDescription": "Doritos Nacho Cheese",
              "price": "3.35"
            },{
              "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
              "price": "12.00"
            }
          ],
          "total": "35.35"
        }
        """;

        String id = mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiptJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", not(emptyString())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID receiptId = UUID.fromString(objectMapper.readTree(id).get("id").asText());

        mockMvc.perform(get("/receipts/{id}/points", receiptId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points", is(28)));
    }

    @Test
    void testProcessReceiptAndGetPoints_ExampleTwo() throws Exception {
        // Example 2 Input from the original README.md
        String receiptJson = """
        {
          "retailer": "M&M Corner Market",
          "purchaseDate": "2022-03-20",
          "purchaseTime": "14:33",
          "items": [
            {
              "shortDescription": "Gatorade",
              "price": "2.25"
            },{
              "shortDescription": "Gatorade",
              "price": "2.25"
            },{
              "shortDescription": "Gatorade",
              "price": "2.25"
            },{
              "shortDescription": "Gatorade",
              "price": "2.25"
            }
          ],
          "total": "9.00"
        }
        """;

        String id = mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiptJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", not(emptyString())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID receiptId = UUID.fromString(objectMapper.readTree(id).get("id").asText());

        mockMvc.perform(get("/receipts/{id}/points", receiptId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points", is(109)));
    }

    @Test
    void testGetPoints_Returns404_WhenReceiptDoesNotExist() throws Exception {
        UUID notExistingReceiptId = UUID.randomUUID();

        mockMvc.perform(get("/receipts/{id}/points", notExistingReceiptId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testProcessReceipt_Returns400_WhenReceiptIsInvalid() throws Exception {
        String invalidReceiptJson = """
        {
          "retailer": "",
          "purchaseDate": "",
          "purchaseTime": "",
          "items": [],
          "total": ""
        }
        """;

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidReceiptJson))
                .andExpect(status().isBadRequest());
    }

}
