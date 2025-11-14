import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MenubarModule } from 'primeng/menubar';
import { ToastModule } from 'primeng/toast';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { MenuItem } from 'primeng/api';
import { LoadingService } from './core/services/loading.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MenubarModule,
    ToastModule,
    ProgressSpinnerModule
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  menuItems: MenuItem[] = [
    {
      label: 'Servidores',
      icon: 'pi pi-users',
      routerLink: '/servidores'
    },
    {
      label: 'Secretarias',
      icon: 'pi pi-building',
      routerLink: '/secretarias'
    }
  ];

  constructor(public loadingService: LoadingService) {}
}
