import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { SecretariaService } from '../../services/secretaria.service';
import { NotificationService } from '../../../../core/services/notification.service';

@Component({
  selector: 'app-secretaria-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, InputTextModule, ButtonModule],
  templateUrl: './secretaria-form.component.html',
  styleUrls: ['./secretaria-form.component.css']
})
export class SecretariaFormComponent implements OnInit {
  secretariaForm!: FormGroup;
  isEditMode = false;
  isSubmitting = false;
  secretariaId?: number;

  constructor(
    private fb: FormBuilder,
    private secretariaService: SecretariaService,
    private router: Router,
    private route: ActivatedRoute,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.initForm();

    this.secretariaForm.get('sigla')?.valueChanges.subscribe(value => {
      if (value && value !== value.toUpperCase()) {
        this.secretariaForm.patchValue(
          { sigla: value.toUpperCase() },
          { emitEvent: false }
        );
      }
    });

    this.secretariaId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.secretariaId) {
      this.isEditMode = true;
      this.loadSecretaria(this.secretariaId);
    }
  }

  private initForm(): void {
    this.secretariaForm = this.fb.group({
      nome: ['', [Validators.required, Validators.maxLength(100)]],
      sigla: ['', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(10),
        Validators.pattern(/^[A-Z]+$/)
      ]]
    });
  }

  private loadSecretaria(id: number): void {
    this.secretariaService.getById(id).subscribe({
      next: (secretaria) => {
        this.secretariaForm.patchValue({
          nome: secretaria.nome,
          sigla: secretaria.sigla
        });
      }
    });
  }

  onSubmit(): void {
    if (this.secretariaForm.invalid) {
      this.secretariaForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    const formValue = this.secretariaForm.value;

    const request = this.isEditMode
      ? this.secretariaService.update(this.secretariaId!, formValue)
      : this.secretariaService.create(formValue);

    request.subscribe({
      next: () => {
        this.notificationService.showSuccess(
          'Sucesso',
          `Secretaria ${this.isEditMode ? 'atualizada' : 'criada'} com sucesso`
        );
        this.router.navigate(['/secretarias']);
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
    this.router.navigate(['/secretarias']);
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.secretariaForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  getErrorMessage(fieldName: string): string {
    const field = this.secretariaForm.get(fieldName);
    if (!field || !field.errors) return '';

    const errors = field.errors;
    if (errors['required']) return 'Campo obrigatório';
    if (errors['maxlength']) return `Máximo ${errors['maxlength'].requiredLength} caracteres`;
    if (errors['minlength']) return `Mínimo ${errors['minlength'].requiredLength} caracteres`;
    if (errors['pattern']) return 'Sigla deve conter apenas letras maiúsculas';

    return 'Campo inválido';
  }
}
