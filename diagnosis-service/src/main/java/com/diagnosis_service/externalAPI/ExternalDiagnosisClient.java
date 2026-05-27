package com.diagnosis_service.externalAPI;

import com.diagnosis_service.entity.Diagnosis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class ExternalDiagnosisClient {
    private final WebClient wc;
    private final String base;

    public ExternalDiagnosisClient(@Value("${external.api.url}") String base) {
        this.wc = WebClient.builder().build();
        this.base = base;
    }

    public List<Diagnosis> getAll() {
        return wc.get()
                .uri(base)
                .retrieve()
                .bodyToFlux(Diagnosis.class)
                .collectList()
                .block();
    }

    public Diagnosis create(Diagnosis d) {
        return wc.post()
                .uri(base)
                .bodyValue(d)
                .retrieve()
                .bodyToMono(Diagnosis.class)
                .block();
    }
}