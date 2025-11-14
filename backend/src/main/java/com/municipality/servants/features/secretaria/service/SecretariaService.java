package com.municipality.servants.features.secretaria.service;

import com.municipality.servants.features.secretaria.dto.SecretariaRequestDTO;
import com.municipality.servants.features.secretaria.dto.SecretariaResponseDTO;

import java.util.List;

public interface SecretariaService {

    SecretariaResponseDTO create(SecretariaRequestDTO dto);

    SecretariaResponseDTO update(Long id, SecretariaRequestDTO dto);

    SecretariaResponseDTO findById(Long id);

    List<SecretariaResponseDTO> findAll();

    void delete(Long id);
}
