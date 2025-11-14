import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function ageRangeValidator(min: number = 18, max: number = 75): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    if (!control.value) {
      return null;
    }

    const birthDate = new Date(control.value);
    const today = new Date();

    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();

    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
      age--;
    }

    if (age < min) {
      return {
        ageRange: {
          message: `Idade mínima: ${min} anos (idade calculada: ${age} anos)`
        }
      };
    }

    if (age > max) {
      return {
        ageRange: {
          message: `Idade máxima: ${max} anos (idade calculada: ${age} anos)`
        }
      };
    }

    return null;
  };
}

export function calculateAge(birthDate: string | Date): number | null {
  if (!birthDate) {
    return null;
  }

  const birth = new Date(birthDate);
  const today = new Date();

  let age = today.getFullYear() - birth.getFullYear();
  const monthDiff = today.getMonth() - birth.getMonth();

  if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
    age--;
  }

  return age;
}
