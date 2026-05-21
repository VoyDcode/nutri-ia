package br.com.fiap.nutriai.controller;

import br.com.fiap.nutriai.dto.ChatRequest;
import br.com.fiap.nutriai.dto.ChatResponse;
import br.com.fiap.nutriai.service.AgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        String response = agentService.chat(request.sessionId(), request.message());
        return ResponseEntity.ok(new ChatResponse(response));
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> clearSession(@PathVariable String sessionId) {
        agentService.clearSession(sessionId);
        return ResponseEntity.noContent().build();
    }
}
