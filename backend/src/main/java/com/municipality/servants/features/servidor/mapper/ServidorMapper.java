package com.municipality.servants.features.servidor.mapper;

import com.municipality.servants.features.secretaria.entity.Secretaria;
import com.municipality.servants.features.secretaria.mapper.SecretariaMapper;
import com.municipality.servants.features.servidor.dto.ServidorRequestDTO;
import com.municipality.servants.features.servidor.dto.ServidorResponseDTO;
import com.municipality.servants.features.servidor.entity.Servidor;
import org.mapstruct.*;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {SecretariaMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ServidorMapper {

    @Mapping(target = "secretaria", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Servidor toEntity(ServidorRequestDTO dto);

    @Mapping(source = "secretaria", target = "secretaria")
    @Mapping(target = "idade", expression = "java(entity.getIdade())")
    ServidorResponseDTO toDTO(Servidor entity);

    List<ServidorResponseDTO> toDTOList(List<Servidor> entities);

    @Mapping(target = "secretaria", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(ServidorRequestDTO dto, @MappingTarget Servidor entity);

}
