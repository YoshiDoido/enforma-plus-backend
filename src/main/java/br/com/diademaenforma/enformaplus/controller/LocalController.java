package br.com.diademaenforma.enformaplus.controller;

import br.com.diademaenforma.enformaplus.model.local.LocalDTO;
import br.com.diademaenforma.enformaplus.model.local.LocalResponseDTO;
import br.com.diademaenforma.enformaplus.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/local")
public class LocalController {

    @Autowired
    private LocalService localService;

    @PostMapping("/adicionar")
    public ResponseEntity<?> criar(@RequestBody LocalDTO dto) {
        try {
            LocalDTO novoLocal = localService.salvarLocal(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoLocal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao adicionar local.");
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> listar() {
        List<LocalResponseDTO> lista = localService.listarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum local encontrado.");
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            LocalResponseDTO local = localService.buscarPorId(id);
            return ResponseEntity.ok(local);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody LocalDTO dto) {
        try {
            LocalDTO atualizado = localService.atualizarLocal(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            localService.deletarLocal(id);
            return ResponseEntity.ok("Local deletado com sucesso.");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
}

