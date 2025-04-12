package br.com.diademaenforma.enformaplus.service;


import br.com.diademaenforma.enformaplus.model.user.Especialidade;
import br.com.diademaenforma.enformaplus.model.user.Papel;
import br.com.diademaenforma.enformaplus.model.user.User;
import br.com.diademaenforma.enformaplus.model.user.UserDTO;
import br.com.diademaenforma.enformaplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return convertToDTO(user);
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        return convertToDTO(userRepository.save(user));
    }

    public List<UserDTO> showAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDTO).toList();
    }

    public UserDTO updateUser(UserDTO userDTO) {
        User existingUser = userRepository.findById(userDTO.getId()).orElse(null);
        if (existingUser == null) return null;

        if (userDTO.getUsuario() != null)
            existingUser.setUsuario(userDTO.getUsuario());
        if (userDTO.getEmail() != null)
            existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getSenha() != null)
            existingUser.setSenha(userDTO.getSenha());
        if (userDTO.getPapel() != null)
            existingUser.setPapel(Papel.valueOf(userDTO.getPapel()));
        if (userDTO.getEspecialidade() != null)
            existingUser.setEspecialidade(Especialidade.valueOf(userDTO.getEspecialidade()));

        return convertToDTO(userRepository.save(existingUser));
    }

    public boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<UserDTO> findByPapel(String papel) {
        return userRepository.findByPapel(Papel.valueOf(papel.toUpperCase()))
                .stream().map(this::convertToDTO).toList();
    }

    public List<UserDTO> findByEspecialidade(String especialidade) {
        return userRepository.findByEspecialidade(Especialidade.valueOf(especialidade.toUpperCase()))
                .stream().map(this::convertToDTO).toList();
    }

    public List<UserDTO> findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email não pode estar vazio");
        }

        List<User> users = userRepository.findByEmailContainingIgnoreCase(email);
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum usuário com esse email existe");
        }

        return users.stream().map(this::convertToDTO).toList();
    }

    private UserDTO convertToDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsuario(user.getUsuario());
        dto.setEmail(user.getEmail());
        dto.setSenha(user.getSenha());
        dto.setPapel(user.getPapel() != null ? user.getPapel().name() : null);
        dto.setEspecialidade(user.getEspecialidade() != null ? user.getEspecialidade().name() : null);
        dto.setDataCriacao(user.getDataCriacao());
        dto.setDataAtualizacao(user.getDataAtualizacao());
        return dto;
    }


    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsuario(dto.getUsuario());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        if (dto.getPapel() != null)
            user.setPapel(Papel.valueOf(dto.getPapel()));
        if (dto.getEspecialidade() != null)
            user.setEspecialidade(Especialidade.valueOf(dto.getEspecialidade()));
        return user;
    }
}
