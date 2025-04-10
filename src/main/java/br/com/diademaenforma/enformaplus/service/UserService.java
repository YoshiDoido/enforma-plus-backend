package br.com.diademaenforma.enformaplus.service;


import br.com.diademaenforma.enformaplus.model.user.Especialidade;
import br.com.diademaenforma.enformaplus.model.user.Papel;
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

    private UserDTO convertToDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsuario(user.getUsuario());
        dto.setEmail(user.getEmail());
        dto.setSenha(user.getSenha());
        dto.setPapel(user.getPapel() != null ? user.getPapel().name() : null);
        dto.setEspecialidade(user.getEspecialidade() != null ? user.getEspecialidade().name() : null);
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
