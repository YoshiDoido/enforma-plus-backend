package br.com.diademaenforma.enformaplus.controller;

import br.com.diademaenforma.enformaplus.model.local.Local;
import br.com.diademaenforma.enformaplus.model.user.Papel;
import br.com.diademaenforma.enformaplus.model.user.User;
import br.com.diademaenforma.enformaplus.repository.LocalRepository;
import br.com.diademaenforma.enformaplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/associar-profissional")
public class AssociarProfissionalController {

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{localId}/{userId}")
    public String associarProfissional(@PathVariable Long localId, @PathVariable Long userId) {
        Local local = localRepository.findById(localId).orElseThrow(() -> new RuntimeException("Local não encontrado"));
        User usuario = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (usuario.getPapel() != Papel.USUARIO_PROFISSIONAL) {
            return "Usuário não é profissional";
        }

        usuario.setLocal(local);
        userRepository.save(usuario);
        return "Usuário associado ao local com sucesso";
    }
}
