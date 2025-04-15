package br.com.diademaenforma.enformaplus.controller;

import br.com.diademaenforma.enformaplus.model.local.Local;
import br.com.diademaenforma.enformaplus.model.local.LocalDTO;
import br.com.diademaenforma.enformaplus.model.local.LocalResponseDTO;
import br.com.diademaenforma.enformaplus.model.user.User;
import br.com.diademaenforma.enformaplus.repository.LocalRepository;
import br.com.diademaenforma.enformaplus.repository.UserRepository;
import br.com.diademaenforma.enformaplus.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/local")
public class LocalController {

    @Autowired
    private LocalService localService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/adicionar")
    public LocalDTO criar(@RequestBody LocalDTO dto) {
        return localService.salvarLocal(dto);
    }

    @GetMapping("/buscar")
    public List<LocalResponseDTO> listar() {
        return localService.listarTodos();
    }

    @GetMapping("/buscar/{id}")
    public LocalResponseDTO buscarPorId(@PathVariable Long id) {
        return localService.buscarPorId(id);
    }

    @PutMapping("/atualizar/{id}")
    public LocalDTO atualizar(@PathVariable Long id, @RequestBody LocalDTO dto) {
        return localService.atualizarLocal(id, dto);
    }

}
