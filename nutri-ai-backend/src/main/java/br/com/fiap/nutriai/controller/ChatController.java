package br.com.fiap.nutriai.controller;

import br.com.fiap.nutriai.dto.ChatRequest;
import br.com.fiap.nutriai.dto.ChatResponse;
import br.com.fiap.nutriai.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        String response = chatService.chat(request.message());
        return ResponseEntity.ok(new ChatResponse(response));
    }
}
