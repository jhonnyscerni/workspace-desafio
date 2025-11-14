package com.municipality.servants.features.secretaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecretariaResponseDTO {

    private Long id;
    private String nome;
    private String sigla;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
