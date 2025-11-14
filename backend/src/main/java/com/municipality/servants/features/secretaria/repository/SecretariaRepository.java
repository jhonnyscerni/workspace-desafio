package com.municipality.servants.features.secretaria.repository;

import com.municipality.servants.features.secretaria.entity.Secretaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretariaRepository extends JpaRepository<Secretaria, Long> {

    boolean existsBySigla(String sigla);

    boolean existsBySiglaAndIdNot(String sigla, Long id);
}
