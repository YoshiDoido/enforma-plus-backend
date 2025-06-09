package br.com.diademaenforma.enformaplus.controller;

import br.com.diademaenforma.enformaplus.model.acesso.AcessoDTO;
import br.com.diademaenforma.enformaplus.model.acesso.AcessoProfissional;
import br.com.diademaenforma.enformaplus.service.AcessoProfissionalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acessos")
public class AcessoProfissionalController {

    private final AcessoProfissionalService acessoProfissionalService;

    public AcessoProfissionalController(AcessoProfissionalService service) {
        this.acessoProfissionalService = service;
    }

    @GetMapping
    public List<AcessoProfissional> listarTodosAcessos() {
        return acessoProfissionalService.buscarTodosAcessos();
    }

    @PostMapping
    public String registrarAcesso(@RequestBody AcessoDTO dto) {
        acessoProfissionalService.registrarAcesso(dto.usuarioId(), dto.profissionalId());
        return "Acesso registrado com sucesso!";
    }
}
