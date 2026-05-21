import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NutriService, NutritionAnalysis } from '../../services/nutri.service';

@Component({
  selector: 'app-analysis',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './analysis.component.html',
  styleUrl: './analysis.component.css'
})
export class AnalysisComponent {
  input = '';
  analysis: NutritionAnalysis | null = null;
  loading = false;
  error = '';

  examples = [
    '2 ovos mexidos com 2 fatias de pão integral e uma banana',
    'Prato de arroz branco (200g), feijão (100g), peito de frango grelhado (150g) e salada',
    'Iogurte natural com granola e frutas vermelhas',
    '1 scoop de whey protein com leite desnatado e aveia'
  ];

  ratingColors: Record<string, string> = {
    Excelente: '#22c55e',
    Bom: '#84cc16',
    Regular: '#f59e0b',
    Ruim: '#ef4444'
  };

  constructor(private nutri: NutriService) {}

  analyze() {
    const text = this.input.trim();
    if (!text || this.loading) return;
    this.loading = true;
    this.analysis = null;
    this.error = '';
    this.nutri.analyzeNutrition(`"${text}"`).subscribe({
      next: res => {
        this.analysis = res;
        this.loading = false;
      },
      error: () => {
        this.error = '❌ Erro ao analisar. Verifique a conexão com o servidor.';
        this.loading = false;
      }
    });
  }

  useExample(ex: string) {
    this.input = ex;
  }

  getRatingColor(): string {
    if (!this.analysis) return '#64748b';
    return this.ratingColors[this.analysis.nutritionRating] ?? '#64748b';
  }
}
