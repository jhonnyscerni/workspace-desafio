import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { NotificationService } from '../services/notification.service';
import { ErrorResponse } from '../models/error-response.model';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const notificationService = inject(NotificationService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'Ocorreu um erro inesperado';

      if (error.error instanceof ErrorEvent) {
        errorMessage = `Erro: ${error.error.message}`;
      } else {
        const errorResponse: ErrorResponse = error.error;

        switch (error.status) {
          case 400:
            errorMessage = handleValidationError(errorResponse);
            break;
          case 404:
            errorMessage = errorResponse?.message || 'Recurso não encontrado';
            break;
          case 422:
            errorMessage = errorResponse?.message || 'Erro de validação';
            break;
          case 500:
            errorMessage = 'Erro no servidor. Tente novamente mais tarde.';
            break;
          default:
            errorMessage = `Erro ${error.status}: ${error.message}`;
        }
      }

      notificationService.showError('Erro', errorMessage);
      return throwError(() => new Error(errorMessage));
    })
  );
};

function handleValidationError(errorResponse: ErrorResponse): string {
  if (errorResponse?.errors) {
    const errors = Object.entries(errorResponse.errors)
      .map(([field, message]) => `${field}: ${message}`)
      .join('\n');
    return errors || errorResponse.message;
  }
  return errorResponse?.message || 'Erro de validação';
}
