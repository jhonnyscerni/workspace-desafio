package com.municipality.servants.features.servidor.controller;

import com.municipality.servants.features.servidor.dto.ServidorRequestDTO;
import com.municipality.servants.features.servidor.dto.ServidorResponseDTO;
import com.municipality.servants.features.servidor.service.ServidorService;
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
@RequestMapping("/api/servidores")
@RequiredArgsConstructor
@Slf4j
public class ServidorController {

    private final ServidorService servidorService;

    @GetMapping
    public ResponseEntity<List<ServidorResponseDTO>> findAll() {
        log.debug("REST request to get all Servidores");
        List<ServidorResponseDTO> servidores = servidorService.findAll();
        return ResponseEntity.ok(servidores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServidorResponseDTO> findById(@PathVariable Long id) {
        log.debug("REST request to get Servidor : {}", id);
        ServidorResponseDTO servidor = servidorService.findById(id);
        return ResponseEntity.ok(servidor);
    }

    @PostMapping
    public ResponseEntity<ServidorResponseDTO> create(@Valid @RequestBody ServidorRequestDTO dto) {
        log.debug("REST request to save Servidor : {}", dto);
        ServidorResponseDTO created = servidorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServidorResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ServidorRequestDTO dto) {
        log.debug("REST request to update Servidor : {}, {}", id, dto);
        ServidorResponseDTO updated = servidorService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Servidor : {}", id);
        servidorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        log.debug("REST request to export Servidores to CSV");

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=servidores.csv");

        List<ServidorResponseDTO> servidores = servidorService.findAll();

        PrintWriter writer = response.getWriter();
        writer.println("ID,Nome,Email,Data Nascimento,Idade,Secretaria,Sigla");

        for (ServidorResponseDTO s : servidores) {
            writer.println(String.format("%d,%s,%s,%s,%d,%s,%s",
                s.getId(),
                s.getNome(),
                s.getEmail(),
                s.getDataNascimento(),
                s.getIdade(),
                s.getSecretaria().getNome(),
                s.getSecretaria().getSigla()
            ));
        }

        writer.flush();
    }
}
