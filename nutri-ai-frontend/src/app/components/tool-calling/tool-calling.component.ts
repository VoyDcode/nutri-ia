import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NutriService } from '../../services/nutri.service';

@Component({
  selector: 'app-tool-calling',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tool-calling.component.html',
  styleUrl: './tool-calling.component.css'
})
export class ToolCallingComponent {
  input = '';
  result = '';
  loading = false;

  examples = [
    'Tenho 28 anos, peso 75kg, altura 1.75m, sou homem e pratico exercícios moderados. Qual meu IMC e quantas calorias devo consumir por dia?',
    'Calcule o IMC de uma pessoa com 60kg e 1.60m de altura.',
    'Preciso saber a ingestão diária de proteína recomendada para uma mulher de 65kg que quer emagrecer.',
    'Quais são as informações nutricionais do frango grelhado e do arroz?'
  ];

  constructor(private nutri: NutriService) {}

  send() {
    const text = this.input.trim();
    if (!text || this.loading) return;
    this.loading = true;
    this.result = '';
    this.nutri.callTools(text).subscribe({
      next: res => {
        this.result = res.response;
        this.loading = false;
      },
      error: () => {
        this.result = '❌ Erro ao conectar com o servidor.';
        this.loading = false;
      }
    });
  }

  useExample(ex: string) {
    this.input = ex;
  }

  onKeydown(event: KeyboardEvent) {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
      this.send();
    }
  }
}
