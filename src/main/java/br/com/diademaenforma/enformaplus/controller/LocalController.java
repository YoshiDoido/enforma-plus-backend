package br.com.diademaenforma.enformaplus.controller;

import br.com.diademaenforma.enformaplus.model.local.Local;
import br.com.diademaenforma.enformaplus.model.local.LocalDTO;
import br.com.diademaenforma.enformaplus.model.user.User;
import br.com.diademaenforma.enformaplus.repository.LocalRepository;
import br.com.diademaenforma.enformaplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/local")
public class LocalController {

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/adicionar")
    public Local criarLocal(@RequestBody LocalDTO localDTO) {
        Local local = new Local();
        local.setNome(localDTO.getNome());

        List<User> profissionais = userRepository.findAllById(localDTO.getUsuariosProfissionais());
        local.setUsuariosProfissionais(profissionais);

        return localRepository.save(local);
    }


    @GetMapping("/buscar")
    public List<Local> listarLocais() {
        return localRepository.findAll();
    }
}
