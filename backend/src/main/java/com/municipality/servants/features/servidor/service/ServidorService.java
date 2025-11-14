package com.municipality.servants.features.servidor.service;

import com.municipality.servants.features.servidor.dto.ServidorRequestDTO;
import com.municipality.servants.features.servidor.dto.ServidorResponseDTO;

import java.util.List;

public interface ServidorService {

    ServidorResponseDTO create(ServidorRequestDTO dto);

    ServidorResponseDTO update(Long id, ServidorRequestDTO dto);

    ServidorResponseDTO findById(Long id);

    List<ServidorResponseDTO> findAll();

    void delete(Long id);
}
