package br.com.diademaenforma.enformaplus.controller;

import br.com.diademaenforma.enformaplus.model.user.UserDTO;
import br.com.diademaenforma.enformaplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(404).body("Usuário com esse ID não encontrado..");
    }

    @GetMapping()
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

}
