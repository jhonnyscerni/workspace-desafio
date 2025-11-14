import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { ServidorService } from '../../services/servidor.service';
import { SecretariaService } from '../../../secretaria/services/secretaria.service';
import { Secretaria } from '../../../../core/models/secretaria.model';
import { NotificationService } from '../../../../core/services/notification.service';
import { ageRangeValidator, calculateAge } from '../../../../shared/validators/age-range.validator';

@Component({
  selector: 'app-servidor-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputTextModule,
    ButtonModule,
    CalendarModule,
    DropdownModule
  ],
  templateUrl: './servidor-form.component.html',
  styleUrls: ['./servidor-form.component.css']
})
export class ServidorFormComponent implements OnInit {
  servidorForm!: FormGroup;
  isEditMode = false;
  isSubmitting = false;
  servidorId?: number;
  secretarias: Secretaria[] = [];
  calculatedAge: number | null = null;
  maxDate: Date = new Date();
  yearRange: string;

  constructor(
    private fb: FormBuilder,
    private servidorService: ServidorService,
    private secretariaService: SecretariaService,
    private router: Router,
    private route: ActivatedRoute,
    private notificationService: NotificationService
  ) {
    const currentYear = new Date().getFullYear();
    const minYear = currentYear - 75;
    const maxYear = currentYear - 18;
    this.yearRange = `${minYear}:${maxYear}`;
  }

  ngOnInit(): void {
    this.initForm();
    this.loadSecretarias();

    this.servidorId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.servidorId) {
      this.isEditMode = true;
      this.loadServidor(this.servidorId);
    }

    this.servidorForm.get('dataNascimento')?.valueChanges.subscribe(date => {
      if (date) {
        this.calculatedAge = calculateAge(date);
      } else {
        this.calculatedAge = null;
      }
    });
  }

  private initForm(): void {
    this.servidorForm = this.fb.group({
      nome: ['', [Validators.required, Validators.maxLength(100)]],
      email: ['', [Validators.required, Validators.email]],
      dataNascimento: ['', [Validators.required, ageRangeValidator(18, 75)]],
      secretariaId: [null, Validators.required]
    });
  }

  private loadSecretarias(): void {
    this.secretariaService.getAll().subscribe({
      next: (data) => {
        this.secretarias = data;
      },
      error: () => {
        this.notificationService.showError(
          'Erro',
          'Não foi possível carregar as secretarias'
        );
      }
    });
  }

  private loadServidor(id: number): void {
    this.servidorService.getById(id).subscribe({
      next: (servidor) => {
        this.servidorForm.patchValue({
          nome: servidor.nome,
          email: servidor.email,
          dataNascimento: new Date(servidor.dataNascimento),
          secretariaId: servidor.secretaria.id
        });
      }
    });
  }

  onSubmit(): void {
    if (this.servidorForm.invalid) {
      this.servidorForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    const formValue = { ...this.servidorForm.value };

    if (formValue.dataNascimento instanceof Date) {
      const date = formValue.dataNascimento;
      formValue.dataNascimento = date.toISOString().split('T')[0];
    }

    const request = this.isEditMode
      ? this.servidorService.update(this.servidorId!, formValue)
      : this.servidorService.create(formValue);

    request.subscribe({
      next: () => {
        this.notificationService.showSuccess(
          'Sucesso',
          `Servidor ${this.isEditMode ? 'atualizado' : 'criado'} com sucesso`
        );
        this.router.navigate(['/servidores']);
      },
      error: () => {
        this.isSubmitting = false;
      },
      complete: () => {
        this.isSubmitting = false;
      }
    });
  }

  cancel(): void {
    this.router.navigate(['/servidores']);
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.servidorForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  getErrorMessage(fieldName: string): string {
    const field = this.servidorForm.get(fieldName);
    if (!field || !field.errors) return '';

    const errors = field.errors;
    if (errors['required']) return 'Campo obrigatório';
    if (errors['email']) return 'Email inválido';
    if (errors['maxlength']) return `Máximo ${errors['maxlength'].requiredLength} caracteres`;
    if (errors['ageRange']) return errors['ageRange'].message;

    return 'Campo inválido';
  }
}
