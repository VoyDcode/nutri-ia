package br.com.fiap.nutriai.controller;

import br.com.fiap.nutriai.dto.NutritionAnalysis;
import br.com.fiap.nutriai.service.StructuredOutputService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
public class StructuredOutputController {

    private final StructuredOutputService service;

    public StructuredOutputController(StructuredOutputService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<NutritionAnalysis> analyze(@RequestBody String mealDescription) {
        return ResponseEntity.ok(service.analyze(mealDescription));
    }
}
