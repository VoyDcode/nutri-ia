import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NutriService } from '../../services/nutri.service';

interface Message {
  role: 'user' | 'assistant';
  content: string;
}

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent {
  messages: Message[] = [];
  input = '';
  loading = false;

  constructor(private nutri: NutriService) {}

  send() {
    const text = this.input.trim();
    if (!text || this.loading) return;
    this.messages.push({ role: 'user', content: text });
    this.input = '';
    this.loading = true;
    this.nutri.chat(text).subscribe({
      next: res => {
        this.messages.push({ role: 'assistant', content: res.response });
        this.loading = false;
      },
      error: () => {
        this.messages.push({ role: 'assistant', content: '❌ Erro ao conectar com o servidor.' });
        this.loading = false;
      }
    });
  }

  onKeydown(event: KeyboardEvent) {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
      this.send();
    }
  }
}
