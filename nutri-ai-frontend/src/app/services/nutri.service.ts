import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ChatRequest {
  message: string;
  sessionId?: string;
}

export interface ChatResponse {
  response: string;
}

export interface NutritionAnalysis {
  mealDescription: string;
  foods: FoodItem[];
  totalCalories: number;
  totalProteinGrams: number;
  totalCarbsGrams: number;
  totalFatGrams: number;
  nutritionRating: string;
  recommendations: string[];
}

export interface FoodItem {
  name: string;
  quantity: string;
  calories: number;
  proteinGrams: number;
  carbsGrams: number;
  fatGrams: number;
}

@Injectable({ providedIn: 'root' })
export class NutriService {
  private readonly base = '/api';

  constructor(private http: HttpClient) {}

  chat(message: string): Observable<ChatResponse> {
    return this.http.post<ChatResponse>(`${this.base}/chat`, { message });
  }

  callTools(message: string): Observable<ChatResponse> {
    return this.http.post<ChatResponse>(`${this.base}/tools`, { message });
  }

  analyzeNutrition(mealDescription: string): Observable<NutritionAnalysis> {
    return this.http.post<NutritionAnalysis>(`${this.base}/analysis`, mealDescription, {
      headers: { 'Content-Type': 'application/json' }
    });
  }

  ragQuery(question: string): Observable<ChatResponse> {
    return this.http.post<ChatResponse>(`${this.base}/rag`, { message: question });
  }

  agentChat(message: string, sessionId: string): Observable<ChatResponse> {
    return this.http.post<ChatResponse>(`${this.base}/agent`, { message, sessionId });
  }

  clearAgentSession(sessionId: string): Observable<void> {
    return this.http.delete<void>(`${this.base}/agent/${sessionId}`);
  }
}
