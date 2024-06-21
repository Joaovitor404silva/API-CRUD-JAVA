package com.me.desafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class FilialService {

    private final RestTemplate restTemplate;

    @Autowired
    public FilialService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, String> getFilialById(String idFilial) {
        String url = "http://localhost:8080/filiais";
        ResponseEntity<List<Map<String, String>>> response = restTemplate.exchange(
                url, 
                HttpMethod.GET, 
                null, 
                new ParameterizedTypeReference<List<Map<String, String>>>() {}
        );

        List<Map<String, String>> filiais = response.getBody();
        if (filiais != null) {
            for (Map<String, String> filial : filiais) {
                if (filial.get("id").equals(idFilial)) {
                    return filial;
                }
            }
        }
        return null;
    }
}
