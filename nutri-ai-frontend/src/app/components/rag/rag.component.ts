import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NutriService } from '../../services/nutri.service';

interface Message {
  role: 'user' | 'assistant';
  content: string;
}

@Component({
  selector: 'app-rag',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './rag.component.html',
  styleUrl: './rag.component.css'
})
export class RagComponent {
  messages: Message[] = [];
  input = '';
  loading = false;

  suggestions = [
    'Quais são as melhores fontes de proteína para vegetarianos?',
    'Como calcular minha necessidade calórica diária?',
    'O que é a dieta mediterrânea e quais seus benefícios?',
    'Quais alimentos são ricos em fibras?',
    'Qual a diferença entre carboidratos simples e complexos?',
    'O que é jejum intermitente e para quem é indicado?'
  ];

  constructor(private nutri: NutriService) {}

  send() {
    const text = this.input.trim();
    if (!text || this.loading) return;
    this.messages.push({ role: 'user', content: text });
    this.input = '';
    this.loading = true;
    this.nutri.ragQuery(text).subscribe({
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

  useSuggestion(s: string) {
    this.input = s;
  }

  onKeydown(event: KeyboardEvent) {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
      this.send();
    }
  }
}
