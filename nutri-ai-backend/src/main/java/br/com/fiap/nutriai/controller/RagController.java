package br.com.fiap.nutriai.controller;

import br.com.fiap.nutriai.dto.ChatRequest;
import br.com.fiap.nutriai.dto.ChatResponse;
import br.com.fiap.nutriai.service.RagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rag")
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> query(@RequestBody ChatRequest request) {
        String response = ragService.query(request.message());
        return ResponseEntity.ok(new ChatResponse(response));
    }
}
