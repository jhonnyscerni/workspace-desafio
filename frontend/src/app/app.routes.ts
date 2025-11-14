import { Routes } from '@angular/router';
import { SecretariaListComponent } from './features/secretaria/components/secretaria-list/secretaria-list.component';
import { SecretariaFormComponent } from './features/secretaria/components/secretaria-form/secretaria-form.component';
import { ServidorListComponent } from './features/servidor/components/servidor-list/servidor-list.component';
import { ServidorFormComponent } from './features/servidor/components/servidor-form/servidor-form.component';

export const routes: Routes = [
  { path: '', redirectTo: '/servidores', pathMatch: 'full' },

  // Servidores
  { path: 'servidores', component: ServidorListComponent },
  { path: 'servidores/novo', component: ServidorFormComponent },
  { path: 'servidores/editar/:id', component: ServidorFormComponent },

  // Secretarias
  { path: 'secretarias', component: SecretariaListComponent },
  { path: 'secretarias/novo', component: SecretariaFormComponent },
  { path: 'secretarias/editar/:id', component: SecretariaFormComponent },

  // Fallback
  { path: '**', redirectTo: '/servidores' }
];
