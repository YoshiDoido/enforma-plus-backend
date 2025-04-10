package br.com.diademaenforma.enformaplus.service;


import br.com.diademaenforma.enformaplus.model.user.User;
import br.com.diademaenforma.enformaplus.model.user.UserDTO;
import br.com.diademaenforma.enformaplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Conversão para DTO
    private UserDTO convertToDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsuario(user.getUsuario());
        dto.setEmail(user.getEmail());
        dto.setSenha(user.getSenha());
        dto.setPapel(user.getPapel());
        return dto;
    }

    // Conversão para Entity
    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsuario(dto.getUsuario());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        user.setPapel(dto.getPapel());
        return user;
    }
}
