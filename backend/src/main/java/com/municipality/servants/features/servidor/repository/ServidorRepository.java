package com.municipality.servants.features.servidor.repository;

import com.municipality.servants.features.servidor.entity.Servidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServidorRepository extends JpaRepository<Servidor, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    List<Servidor> findBySecretariaId(Long secretariaId);

    @Query("SELECT s FROM Servidor s JOIN FETCH s.secretaria WHERE s.id = :id")
    Optional<Servidor> findByIdWithSecretaria(@Param("id") Long id);

    @Query("SELECT s FROM Servidor s JOIN FETCH s.secretaria")
    List<Servidor> findAllWithSecretaria();
}
