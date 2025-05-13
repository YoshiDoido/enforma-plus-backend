package br.com.diademaenforma.enformaplus.controller;

import br.com.diademaenforma.enformaplus.model.user.UserDTO;
import br.com.diademaenforma.enformaplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(404).body("Usuário com esse ID não encontrado..");
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> findAllUsers() {
        List<UserDTO> users = userService.showAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.status(404).body("Nenhum usuário encontrado.");
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("/criar")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO created = userService.saveUser(userDTO);
            return ResponseEntity.status(201).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Falha ao criar o usuário.");
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        UserDTO updatedUser = userService.updateUser(userDTO);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.status(404).body("Usuário não encontrado para atualização.");
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (userService.deleteUserById(id)) {
            return ResponseEntity.ok("Usuário deletado com sucesso.");
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado para deletar.");
        }
    }

    @GetMapping("/buscar/papel")
    public ResponseEntity<?> buscarPorPapel(@RequestParam String papel) {
        try {
            var lista = userService.findByPapel(papel.toUpperCase());
            return lista.isEmpty() ? ResponseEntity.status(404).body("Nenhum usuário com esse papel.") : ResponseEntity.ok(lista);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Papel inválido.");
        }
    }

    @GetMapping("/buscar/especialidade")
    public ResponseEntity<?> buscarPorEspecialidade(@RequestParam String especialidade) {
        try {
            var lista = userService.findByEspecialidade(especialidade.toUpperCase());
            return lista.isEmpty() ? ResponseEntity.status(404).body("Nenhum usuário com essa especialidade.") : ResponseEntity.ok(lista);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Especialidade inválida.");
        }
    }

    @GetMapping("/buscar/email")
    public ResponseEntity<?> buscarPorEmail(@RequestParam String email) {
        try {
            var lista = userService.findByEmail(email);
            return lista.isEmpty() ? ResponseEntity.status(404).body("Nenhum usuário com esse email.") : ResponseEntity.ok(lista);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @GetMapping("/buscar/buscar-todos-profissionais")
    public ResponseEntity<?> buscarTodosProfissionais() {
        var lista = userService.findAllProfissionais();
        return lista.isEmpty()
                ? ResponseEntity.status(404).body("Nenhum profissional encontrado.")
                : ResponseEntity.ok(lista);
    }


}
