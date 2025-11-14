package com.municipality.servants.features.servidor.dto;

import com.municipality.servants.core.validation.ValidAge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    private String email;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    @ValidAge(min = 18, max = 75, message = "Idade deve estar entre 18 e 75 anos")
    private LocalDate dataNascimento;

    @NotNull(message = "Secretaria é obrigatória")
    private Long secretariaId;
}
