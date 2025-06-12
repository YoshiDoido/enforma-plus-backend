package br.com.diademaenforma.enformaplus.controller;

import br.com.diademaenforma.enformaplus.model.acesso.AcessoDTO;
import br.com.diademaenforma.enformaplus.model.acesso.AcessoProfissional;
import br.com.diademaenforma.enformaplus.model.acesso.AcessoResponseDTO;
import br.com.diademaenforma.enformaplus.service.AcessoProfissionalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acessos")
public class AcessoProfissionalController {

    private final AcessoProfissionalService service;

    public AcessoProfissionalController(AcessoProfissionalService service) {
        this.service = service;
    }

    @GetMapping
    public List<AcessoResponseDTO> listarTodosAcessos() {
        return service.buscarTodosAcessosDetalhados();
    }

    @PostMapping
    public String registrarAcesso(@RequestBody AcessoDTO dto) {
        service.registrarAcesso(dto.usuarioId(), dto.profissionalId());
        return "Acesso registrado com sucesso!";
    }
}

