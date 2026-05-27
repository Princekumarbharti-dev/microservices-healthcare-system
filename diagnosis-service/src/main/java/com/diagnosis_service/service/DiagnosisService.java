package com.diagnosis_service.service;

import com.diagnosis_service.entity.Diagnosis;
import com.diagnosis_service.externalAPI.ExternalDiagnosisClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DiagnosisService {
    private final ExternalDiagnosisClient cli;

    public DiagnosisService(ExternalDiagnosisClient cli) {
        this.cli = cli;
    }

    public List<Diagnosis> all() {
        List<Diagnosis> ls = cli.getAll();
        return ls == null ? List.of() : ls;
    }

    public List<Diagnosis> filter(Map<String, String> q) {
        List<Diagnosis> ls = all();
        if (q == null || q.isEmpty()) return ls;

        List<Diagnosis> res = new ArrayList<>();

        for (Diagnosis d : ls) {
            boolean ok = true;

            for (Map.Entry<String, String> e : q.entrySet()) {
                String k = e.getKey();
                String v = e.getValue();

                String val = prop(d, k);
                if (val == null || !val.equals(v)) {
                    ok = false;
                    break;
                }
            }

            if (ok) res.add(d);
        }

        return res;
    }

    public Diagnosis add(Diagnosis d) {
        return cli.create(d);
    }

    private String prop(Diagnosis d, String k) {
        if (k.equals("Gender")) return d.getGender();
        if (k.equals("gender")) return d.getGender();
        if (k.equals("age")) return String.valueOf(d.getAge());
        if (k.equals("bp")) return String.valueOf(d.getBp());
        if (k.equals("cholesterol")) return String.valueOf(d.getCholesterol());
        if (k.equals("diabetic")) return d.getDiabetic();
        if (k.equals("smoking_status")) return d.getSmoking_status();
        if (k.equals("pain_type")) return d.getPain_type();
        if (k.equals("treatment")) return d.getTreatment();
        if (k.equals("id")) return d.getId();
        return null;
    }
}