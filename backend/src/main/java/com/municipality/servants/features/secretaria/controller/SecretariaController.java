package com.municipality.servants.features.secretaria.controller;

import com.municipality.servants.features.secretaria.dto.SecretariaRequestDTO;
import com.municipality.servants.features.secretaria.dto.SecretariaResponseDTO;
import com.municipality.servants.features.secretaria.service.SecretariaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/secretarias")
@RequiredArgsConstructor
@Slf4j
public class SecretariaController {

    private final SecretariaService secretariaService;

    @GetMapping
    public ResponseEntity<List<SecretariaResponseDTO>> findAll() {
        log.debug("REST request to get all Secretarias");
        List<SecretariaResponseDTO> secretarias = secretariaService.findAll();
        return ResponseEntity.ok(secretarias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecretariaResponseDTO> findById(@PathVariable Long id) {
        log.debug("REST request to get Secretaria : {}", id);
        SecretariaResponseDTO secretaria = secretariaService.findById(id);
        return ResponseEntity.ok(secretaria);
    }

    @PostMapping
    public ResponseEntity<SecretariaResponseDTO> create(@Valid @RequestBody SecretariaRequestDTO dto) {
        log.debug("REST request to save Secretaria : {}", dto);
        SecretariaResponseDTO created = secretariaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecretariaResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody SecretariaRequestDTO dto) {
        log.debug("REST request to update Secretaria : {}, {}", id, dto);
        SecretariaResponseDTO updated = secretariaService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Secretaria : {}", id);
        secretariaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        log.debug("REST request to export Secretarias to CSV");

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=secretarias.csv");

        List<SecretariaResponseDTO> secretarias = secretariaService.findAll();

        PrintWriter writer = response.getWriter();
        writer.println("ID,Nome,Sigla,Data Criação");

        for (SecretariaResponseDTO s : secretarias) {
            writer.println(String.format("%d,%s,%s,%s",
                s.getId(),
                s.getNome(),
                s.getSigla(),
                s.getCreatedAt()
            ));
        }

        writer.flush();
    }
}
