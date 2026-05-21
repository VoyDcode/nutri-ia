package br.com.fiap.nutriai.controller;

import br.com.fiap.nutriai.dto.ChatRequest;
import br.com.fiap.nutriai.dto.ChatResponse;
import br.com.fiap.nutriai.service.ToolCallingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tools")
public class ToolCallingController {

    private final ToolCallingService toolCallingService;

    public ToolCallingController(ToolCallingService toolCallingService) {
        this.toolCallingService = toolCallingService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> calculate(@RequestBody ChatRequest request) {
        String response = toolCallingService.calculate(request.message());
        return ResponseEntity.ok(new ChatResponse(response));
    }
}
