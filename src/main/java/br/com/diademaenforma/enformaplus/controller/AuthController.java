package br.com.diademaenforma.enformaplus.controller;

import br.com.diademaenforma.enformaplus.model.user.CadastroRequestDTO;
import br.com.diademaenforma.enformaplus.model.user.LoginRequestDTO;
import br.com.diademaenforma.enformaplus.model.user.UserDTO;
import br.com.diademaenforma.enformaplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody CadastroRequestDTO request) {
        UserDTO novoUsuario = new UserDTO();
        novoUsuario.setUsuario(request.getUsuario());
        novoUsuario.setEmail(request.getEmail());
        novoUsuario.setSenha(request.getSenha());
        novoUsuario.setPapel(request.getPapel());                   // Adicionado
        novoUsuario.setEspecialidade(request.getEspecialidade());   // Adicionado

        UserDTO criado = userService.saveUser(novoUsuario);
        return ResponseEntity.status(201).body(criado);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        UserDTO user = userService.login(request.getEmail(), request.getSenha());
        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.status(401).body("Email ou senha inválidos");
    }
}
