package com.municipality.servants.features.secretaria.service.impl;

import com.municipality.servants.core.exception.ResourceNotFoundException;
import com.municipality.servants.core.exception.ValidationException;
import com.municipality.servants.features.secretaria.dto.SecretariaRequestDTO;
import com.municipality.servants.features.secretaria.dto.SecretariaResponseDTO;
import com.municipality.servants.features.secretaria.entity.Secretaria;
import com.municipality.servants.features.secretaria.mapper.SecretariaMapper;
import com.municipality.servants.features.secretaria.repository.SecretariaRepository;
import com.municipality.servants.features.secretaria.service.SecretariaService;
import com.municipality.servants.features.servidor.repository.ServidorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SecretariaServiceImpl implements SecretariaService {

    private final SecretariaRepository secretariaRepository;
    private final ServidorRepository servidorRepository;
    private final SecretariaMapper secretariaMapper;

    @Override
    public SecretariaResponseDTO create(SecretariaRequestDTO dto) {
        log.debug("Creating secretaria with sigla: {}", dto.getSigla());

        if (secretariaRepository.existsBySigla(dto.getSigla())) {
            throw new ValidationException(
                String.format("Já existe uma secretaria com a sigla '%s'", dto.getSigla())
            );
        }

        Secretaria secretaria = secretariaMapper.toEntity(dto);
        Secretaria saved = secretariaRepository.save(secretaria);

        log.info("Secretaria created successfully with ID: {}", saved.getId());
        return secretariaMapper.toDTO(saved);
    }

    @Override
    public SecretariaResponseDTO update(Long id, SecretariaRequestDTO dto) {
        log.debug("Updating secretaria with ID: {}", id);

        Secretaria secretaria = secretariaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Secretaria", id));

        if (secretariaRepository.existsBySiglaAndIdNot(dto.getSigla(), id)) {
            throw new ValidationException(
                String.format("Já existe outra secretaria com a sigla '%s'", dto.getSigla())
            );
        }

        secretariaMapper.updateEntityFromDTO(dto, secretaria);
        Secretaria updated = secretariaRepository.save(secretaria);

        log.info("Secretaria updated successfully with ID: {}", updated.getId());
        return secretariaMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public SecretariaResponseDTO findById(Long id) {
        log.debug("Finding secretaria with ID: {}", id);

        Secretaria secretaria = secretariaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Secretaria", id));

        return secretariaMapper.toDTO(secretaria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecretariaResponseDTO> findAll() {
        log.debug("Finding all secretarias");

        List<Secretaria> secretarias = secretariaRepository.findAll();
        return secretariaMapper.toDTOList(secretarias);
    }

    @Override
    public void delete(Long id) {
        log.debug("Deleting secretaria with ID: {}", id);

        Secretaria secretaria = secretariaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Secretaria", id));

        long servidoresCount = servidorRepository.findBySecretariaId(id).size();
        if (servidoresCount > 0) {
            throw new ValidationException(
                String.format("Não é possível excluir a secretaria '%s' pois existem %d servidor(es) vinculado(s)",
                    secretaria.getNome(), servidoresCount)
            );
        }

        secretariaRepository.delete(secretaria);
        log.info("Secretaria deleted successfully with ID: {}", id);
    }
}
