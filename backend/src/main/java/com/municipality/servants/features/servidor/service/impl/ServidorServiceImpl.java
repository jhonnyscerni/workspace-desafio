package com.municipality.servants.features.servidor.service.impl;

import com.municipality.servants.core.exception.ResourceNotFoundException;
import com.municipality.servants.core.exception.ValidationException;
import com.municipality.servants.features.secretaria.entity.Secretaria;
import com.municipality.servants.features.secretaria.repository.SecretariaRepository;
import com.municipality.servants.features.servidor.dto.ServidorRequestDTO;
import com.municipality.servants.features.servidor.dto.ServidorResponseDTO;
import com.municipality.servants.features.servidor.entity.Servidor;
import com.municipality.servants.features.servidor.mapper.ServidorMapper;
import com.municipality.servants.features.servidor.repository.ServidorRepository;
import com.municipality.servants.features.servidor.service.ServidorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ServidorServiceImpl implements ServidorService {

    private final ServidorRepository servidorRepository;
    private final SecretariaRepository secretariaRepository;
    private final ServidorMapper servidorMapper;

    @Override
    public ServidorResponseDTO create(ServidorRequestDTO dto) {
        log.debug("Creating servidor with email: {}", dto.getEmail());

        if (servidorRepository.existsByEmail(dto.getEmail())) {
            throw new ValidationException(
                String.format("Já existe um servidor com o email '%s'", dto.getEmail())
            );
        }

        Secretaria secretaria = secretariaRepository.findById(dto.getSecretariaId())
            .orElseThrow(() -> new ResourceNotFoundException("Secretaria", dto.getSecretariaId()));

        Servidor servidor = servidorMapper.toEntity(dto);
        servidor.setSecretaria(secretaria);

        Servidor saved = servidorRepository.save(servidor);

        log.info("Servidor created successfully with ID: {} and age: {}", saved.getId(), saved.getIdade());
        return servidorMapper.toDTO(saved);
    }

    @Override
    public ServidorResponseDTO update(Long id, ServidorRequestDTO dto) {
        log.debug("Updating servidor with ID: {}", id);

        Servidor servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Servidor", id));

        if (servidorRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new ValidationException(
                String.format("Já existe outro servidor com o email '%s'", dto.getEmail())
            );
        }

        Secretaria secretaria = secretariaRepository.findById(dto.getSecretariaId())
            .orElseThrow(() -> new ResourceNotFoundException("Secretaria", dto.getSecretariaId()));

        servidorMapper.updateEntityFromDTO(dto, servidor);
        servidor.setSecretaria(secretaria);

        Servidor updated = servidorRepository.save(servidor);

        log.info("Servidor updated successfully with ID: {} and age: {}", updated.getId(), updated.getIdade());
        return servidorMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public ServidorResponseDTO findById(Long id) {
        log.debug("Finding servidor with ID: {}", id);

        Servidor servidor = servidorRepository.findByIdWithSecretaria(id)
            .orElseThrow(() -> new ResourceNotFoundException("Servidor", id));

        return servidorMapper.toDTO(servidor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServidorResponseDTO> findAll() {
        log.debug("Finding all servidores");

        List<Servidor> servidores = servidorRepository.findAllWithSecretaria();
        return servidorMapper.toDTOList(servidores);
    }

    @Override
    public void delete(Long id) {
        log.debug("Deleting servidor with ID: {}", id);

        Servidor servidor = servidorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Servidor", id));

        servidorRepository.delete(servidor);
        log.info("Servidor deleted successfully with ID: {}", id);
    }
}
