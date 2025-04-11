package br.com.diademaenforma.enformaplus.controller;

import br.com.diademaenforma.enformaplus.model.agendamento.AgendamentoRequestDTO;
import br.com.diademaenforma.enformaplus.model.agendamento.AgendamentoResponseDTO;
import br.com.diademaenforma.enformaplus.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    @Autowired
    AgendamentoService agendamentoService;

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarAgendamentos() {
        return ResponseEntity.ok(agendamentoService.getAllAgendamentos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.getAgendamentoById(id));
    }

    @PostMapping("/criar")
    public ResponseEntity<AgendamentoResponseDTO> criarAgendamento(@RequestBody AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO response = agendamentoService.createAgendamento(dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        agendamentoService.deleteAgendamento(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AgendamentoResponseDTO> atualizarStatus(@PathVariable Long id, @RequestParam String status) {
        AgendamentoResponseDTO response = agendamentoService.updateStatus(id, status);
        return ResponseEntity.ok(response);
    }

}
