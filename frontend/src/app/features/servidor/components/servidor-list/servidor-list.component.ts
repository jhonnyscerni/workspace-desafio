import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';
import { ServidorService } from '../../services/servidor.service';
import { Servidor } from '../../../../core/models/servidor.model';
import { NotificationService } from '../../../../core/services/notification.service';

@Component({
  selector: 'app-servidor-list',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule, ConfirmDialogModule],
  providers: [ConfirmationService],
  templateUrl: './servidor-list.component.html',
  styleUrls: ['./servidor-list.component.css']
})
export class ServidorListComponent implements OnInit {
  servidores: Servidor[] = [];
  loading = false;
  exportCsvUrl: string;

  constructor(
    private servidorService: ServidorService,
    private router: Router,
    private confirmationService: ConfirmationService,
    private notificationService: NotificationService
  ) {
    this.exportCsvUrl = this.servidorService.getExportCsvUrl();
  }

  ngOnInit(): void {
    this.loadServidores();
  }

  loadServidores(): void {
    this.loading = true;
    this.servidorService.getAll().subscribe({
      next: (data) => {
        this.servidores = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  navigateToCreate(): void {
    this.router.navigate(['/servidores/novo']);
  }

  edit(id: number): void {
    this.router.navigate(['/servidores/editar', id]);
  }

  confirmDelete(servidor: Servidor): void {
    this.confirmationService.confirm({
      message: `Tem certeza que deseja excluir o servidor "${servidor.nome}"?`,
      header: 'Confirmação de Exclusão',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.delete(servidor.id!);
      }
    });
  }

  delete(id: number): void {
    this.servidorService.delete(id).subscribe({
      next: () => {
        this.notificationService.showSuccess(
          'Sucesso',
          'Servidor excluído com sucesso'
        );
        this.loadServidores();
      }
    });
  }
}
