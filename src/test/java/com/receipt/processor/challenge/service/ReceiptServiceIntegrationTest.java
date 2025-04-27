package com.receipt.processor.challenge.service;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Tag("e2e")
class ReceiptServiceIntegrationTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080";

    @Test
    void testSubmitReceiptAndGetPoints() {
        final String jsonReceipt = """
                    {
                        "retailer": "Target",
                        "purchaseDate": "2022-01-01",
                        "purchaseTime": "13:01",
                        "total": "35.35",
                        "items": [
                            {"shortDescription": "Mountain Dew 12PK", "price": "6.49"},
                            {"shortDescription": "Emils Cheese Pizza", "price": "12.25"},
                            {"shortDescription": "Knorr Creamy Chicken", "price": "1.26"},
                            {"shortDescription": "Doritos Nacho Cheese", "price": "3.35"},
                            {"shortDescription": "Klarbrunn 12-PK 12 FL OZ", "price": "12.00"}
                        ]
                    }
                """;

        final var headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        final var request = new org.springframework.http.HttpEntity<>(jsonReceipt, headers);

        // Post receipt
        final var response = restTemplate.postForEntity(baseUrl + "/receipts/process", request, String.class);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        log.info("Response Body: {}", response.getBody());

        // Parse id
        final String body = response.getBody();
        final String id = JsonPath.read(body, "$.id");
        log.info("Parsed ID: {}", id);

        // GET points
        final var getResponse = restTemplate.getForEntity(
                baseUrl + "/receipts/" + id + "/points", String.class
        );

        assertEquals(200, getResponse.getStatusCodeValue());
        assertNotNull(getResponse.getBody());
        log.info("GET Points Response: {}", getResponse.getBody());
        final int points = JsonPath.read(getResponse.getBody(), "$.points");
        assertEquals(28, points);
    }
}

