package com.municipality.servants.features.secretaria.mapper;

import com.municipality.servants.features.secretaria.dto.SecretariaRequestDTO;
import com.municipality.servants.features.secretaria.dto.SecretariaResponseDTO;
import com.municipality.servants.features.secretaria.entity.Secretaria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SecretariaMapper {

    Secretaria toEntity(SecretariaRequestDTO dto);

    SecretariaResponseDTO toDTO(Secretaria entity);

    List<SecretariaResponseDTO> toDTOList(List<Secretaria> entities);

    void updateEntityFromDTO(SecretariaRequestDTO dto, @MappingTarget Secretaria entity);
}
