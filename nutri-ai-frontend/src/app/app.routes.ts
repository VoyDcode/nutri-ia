import { Routes } from '@angular/router';
import { ChatComponent } from './components/chat/chat.component';
import { ToolCallingComponent } from './components/tool-calling/tool-calling.component';
import { AnalysisComponent } from './components/analysis/analysis.component';
import { RagComponent } from './components/rag/rag.component';
import { AgentComponent } from './components/agent/agent.component';

export const routes: Routes = [
  { path: '', redirectTo: '/chat', pathMatch: 'full' },
  { path: 'chat', component: ChatComponent },
  { path: 'tools', component: ToolCallingComponent },
  { path: 'analysis', component: AnalysisComponent },
  { path: 'rag', component: RagComponent },
  { path: 'agent', component: AgentComponent },
  { path: '**', redirectTo: '/chat' }
];
