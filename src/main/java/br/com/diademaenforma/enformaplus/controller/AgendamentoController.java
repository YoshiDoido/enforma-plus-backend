package br.com.diademaenforma.enformaplus.controller;

import br.com.diademaenforma.enformaplus.exceptions.UsuarioNaoEncontradoException;
import br.com.diademaenforma.enformaplus.model.agendamento.AgendamentoRequestDTO;
import br.com.diademaenforma.enformaplus.model.agendamento.AgendamentoResponseDTO;
import br.com.diademaenforma.enformaplus.model.agendamento.AtualizarStatusDTO;
import br.com.diademaenforma.enformaplus.model.agendamento.Tipo;
import br.com.diademaenforma.enformaplus.model.user.Especialidade;
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

    @GetMapping("/buscar")
    public ResponseEntity<?> listarAgendamentos() {
        List<AgendamentoResponseDTO> lista = agendamentoService.getAllAgendamentos();
        if (lista.isEmpty()) {
            return ResponseEntity.status(404).body("Nenhum agendamento encontrado.");
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        AgendamentoResponseDTO agendamento = agendamentoService.getAgendamentoById(id);
        if (agendamento != null) {
            return ResponseEntity.ok(agendamento);
        }
        return ResponseEntity.status(404).body("Agendamento com esse ID não encontrado.");
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criarAgendamento(@RequestBody AgendamentoRequestDTO dto) {
        try {
            AgendamentoResponseDTO response = agendamentoService.createAgendamento(dto);
            return ResponseEntity.status(201).body(response);
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao criar o agendamento.");
        }
    }


    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            agendamentoService.deleteAgendamento(id);
            return ResponseEntity.ok("Agendamento deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Agendamento não encontrado para deletar.");
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusDTO dto) {
        try {
            AgendamentoResponseDTO response = agendamentoService.updateStatus(id, dto.getStatus());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao atualizar o status do agendamento.");
        }
    }

    @GetMapping("/buscar/especialidade/{especialidade}")
    public ResponseEntity<?> buscarPorEspecialidade(@PathVariable String especialidade) {
        try {
            var lista = agendamentoService.getAgendamentoByEspecialidade(Especialidade.valueOf(especialidade.toUpperCase()));
            return lista.isEmpty() ? ResponseEntity.status(404).body("Nenhum agendamento com essa especialidade.") : ResponseEntity.ok(lista);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Especialidade inválida.");
        }
    }

    @GetMapping("/buscar/tipo/{tipo}")
    public ResponseEntity<?> buscarPorTipo(@PathVariable String tipo) {
        try {
            var lista = agendamentoService.getAgendamentosPorTipo(Tipo.valueOf(tipo.toUpperCase()));
            return lista.isEmpty() ? ResponseEntity.status(404).body("Nenhum agendamento com esse tipo.") : ResponseEntity.ok(lista);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Tipo de consulta inválido.");
        }
    }

    @GetMapping("/buscar/cliente/{id}")
    public ResponseEntity<?> buscarPorUsuarioCliente(@PathVariable Long id) {
        var lista = agendamentoService.getAgendamentosPorUsuarioCliente(id);
        return lista.isEmpty() ? ResponseEntity.status(404).body("Nenhum agendamento para esse usuário.") : ResponseEntity.ok(lista);
    }

    @GetMapping("/buscar/profissional/{id}")
    public ResponseEntity<?> buscarPorProfissional(@PathVariable Long id) {
        var lista = agendamentoService.getAgendamentosPorProfissional(id);
        return lista.isEmpty() ? ResponseEntity.status(404).body("Nenhum agendamento para esse profissional.") : ResponseEntity.ok(lista);
    }

    @GetMapping("/buscar/cliente/nome/{nome}")
    public ResponseEntity<?> buscarPorNomeCliente(@PathVariable String nome) {
        var lista = agendamentoService.getAgendamentosPorNomeCliente(nome);
        return lista.isEmpty() ? ResponseEntity.status(404).body("Nenhum agendamento para esse cliente.") : ResponseEntity.ok(lista);
    }

    @GetMapping("/buscar/profissional/nome/{nome}")
    public ResponseEntity<?> buscarPorNomeProfissional(@PathVariable String nome) {
        var lista = agendamentoService.getAgendamentosPorNomeProfissional(nome);
        return lista.isEmpty() ? ResponseEntity.status(404).body("Nenhum agendamento para esse profissional.") : ResponseEntity.ok(lista);
    }


}
