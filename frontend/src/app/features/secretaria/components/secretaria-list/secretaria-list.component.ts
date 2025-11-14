import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';
import { SecretariaService } from '../../services/secretaria.service';
import { Secretaria } from '../../../../core/models/secretaria.model';
import { NotificationService } from '../../../../core/services/notification.service';

@Component({
  selector: 'app-secretaria-list',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule, ConfirmDialogModule],
  providers: [ConfirmationService],
  templateUrl: './secretaria-list.component.html',
  styleUrls: ['./secretaria-list.component.css']
})
export class SecretariaListComponent implements OnInit {
  secretarias: Secretaria[] = [];
  loading = false;
  exportCsvUrl: string;

  constructor(
    private secretariaService: SecretariaService,
    private router: Router,
    private confirmationService: ConfirmationService,
    private notificationService: NotificationService
  ) {
    this.exportCsvUrl = this.secretariaService.getExportCsvUrl();
  }

  ngOnInit(): void {
    this.loadSecretarias();
  }

  loadSecretarias(): void {
    this.loading = true;
    this.secretariaService.getAll().subscribe({
      next: (data) => {
        this.secretarias = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  navigateToCreate(): void {
    this.router.navigate(['/secretarias/novo']);
  }

  edit(id: number): void {
    this.router.navigate(['/secretarias/editar', id]);
  }

  confirmDelete(secretaria: Secretaria): void {
    this.confirmationService.confirm({
      message: `Tem certeza que deseja excluir a secretaria "${secretaria.nome}"?`,
      header: 'Confirmação de Exclusão',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.delete(secretaria.id!);
      }
    });
  }

  delete(id: number): void {
    this.secretariaService.delete(id).subscribe({
      next: () => {
        this.notificationService.showSuccess(
          'Sucesso',
          'Secretaria excluída com sucesso'
        );
        this.loadSecretarias();
      }
    });
  }
}
