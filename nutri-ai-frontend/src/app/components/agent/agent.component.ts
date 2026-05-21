import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NutriService } from '../../services/nutri.service';

interface Message {
  role: 'user' | 'assistant';
  content: string;
  timestamp: Date;
}

@Component({
  selector: 'app-agent',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './agent.component.html',
  styleUrl: './agent.component.css'
})
export class AgentComponent implements OnInit {
  messages: Message[] = [];
  input = '';
  loading = false;
  sessionId = '';

  constructor(private nutri: NutriService) {}

  ngOnInit() {
    this.sessionId = crypto.randomUUID();
    this.messages.push({
      role: 'assistant',
      content: 'Olá! Sou o NutriAgent 🤖, seu assistente nutricional pessoal com memória de conversa.\n\nPosso criar um plano alimentar personalizado para você! Para isso, vou precisar de algumas informações:\n\n• Nome e idade\n• Peso e altura\n• Sexo\n• Nível de atividade física\n• Objetivo (emagrecer, manter peso ou ganhar massa)\n\nPor onde quer começar?',
      timestamp: new Date()
    });
  }

  send() {
    const text = this.input.trim();
    if (!text || this.loading) return;
    this.messages.push({ role: 'user', content: text, timestamp: new Date() });
    this.input = '';
    this.loading = true;
    this.nutri.agentChat(text, this.sessionId).subscribe({
      next: res => {
        this.messages.push({ role: 'assistant', content: res.response, timestamp: new Date() });
        this.loading = false;
      },
      error: () => {
        this.messages.push({ role: 'assistant', content: '❌ Erro ao conectar com o servidor.', timestamp: new Date() });
        this.loading = false;
      }
    });
  }

  resetSession() {
    this.nutri.clearAgentSession(this.sessionId).subscribe();
    this.sessionId = crypto.randomUUID();
    this.messages = [];
    this.ngOnInit();
  }

  onKeydown(event: KeyboardEvent) {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
      this.send();
    }
  }
}
