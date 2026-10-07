package com.example.ecomm.services;

import com.example.ecomm.dtos.GroqResponseDto;
import com.example.ecomm.models.Product;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class PitchService {

    private static final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String MODEL = "openai/gpt-oss-20b";
    private static final String API_KEY = "xx";


    private final RestTemplate restTemplate;


    public PitchService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String generatePitch(Product product) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(API_KEY);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        List<Map<String, Object>> messages = new ArrayList<>();
        Map<String, Object> body = Map.of(
            "model", "openai/gpt-oss-20b",
            "messages", messages
        );

        ResponseEntity<GroqResponseDto> responseEntity =
                restTemplate.postForEntity(GROQ_URL, new HttpEntity<>(body, httpHeaders), GroqResponseDto.class);

        return ":";
    }
}
