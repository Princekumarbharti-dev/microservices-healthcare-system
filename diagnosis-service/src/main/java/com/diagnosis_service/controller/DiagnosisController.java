package com.diagnosis_service.controller;

import com.diagnosis_service.entity.Diagnosis;
import com.diagnosis_service.service.DiagnosisService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diagnosis")
public class DiagnosisController {
    private final DiagnosisService svc;

    public DiagnosisController(DiagnosisService svc) {
        this.svc = svc;
    }

    @GetMapping
    public List<Diagnosis> get(@RequestParam Map<String, String> q) {
        return svc.filter(q);
    }

    @PostMapping
    public Diagnosis post(@RequestBody Diagnosis d) {
        return svc.add(d);
    }
}

