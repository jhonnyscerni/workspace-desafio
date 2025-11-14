package com.municipality.servants.features.servidor.dto;

import com.municipality.servants.features.secretaria.dto.SecretariaResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private Integer idade;
    private SecretariaResponseDTO secretaria;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
