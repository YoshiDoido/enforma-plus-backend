package br.com.diademaenforma.enformaplus.service;

import br.com.diademaenforma.enformaplus.model.local.Local;
import br.com.diademaenforma.enformaplus.model.local.LocalDTO;
import br.com.diademaenforma.enformaplus.model.local.LocalResponseDTO;
import br.com.diademaenforma.enformaplus.model.user.User;
import br.com.diademaenforma.enformaplus.repository.LocalRepository;
import br.com.diademaenforma.enformaplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalService {

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private UserRepository userRepository;

    public LocalDTO salvarLocal(LocalDTO dto) {
        Local local = convertToEntity(dto);
        return convertToDTO(localRepository.save(local));
    }

    public List<LocalResponseDTO> listarTodos() {
        return localRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO) // ✅ certo
                .toList();
    }

    public LocalResponseDTO buscarPorId(Long id) {
        Local local = localRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Local não encontrado"));
        return convertToResponseDTO(local); // ✅ certo
    }

    public LocalDTO atualizarLocal(Long id, LocalDTO dto) {
        Local local = localRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Local não encontrado"));
        local.setNome(dto.getNome());

        return convertToDTO(localRepository.save(local));
    }

    public void deletarLocal(Long id) {
        if (!localRepository.existsById(id)) {
            throw new RuntimeException("Local não encontrado");
        }
        localRepository.deleteById(id);
    }

    private Local convertToEntity(LocalDTO dto) {
        Local local = new Local();
        local.setId(dto.getId());
        local.setNome(dto.getNome());
        return local;
    }

    private LocalDTO convertToDTO(Local local) {
        LocalDTO dto = new LocalDTO();
        dto.setId(local.getId());
        dto.setNome(local.getNome());
        return dto;
    }

    private LocalResponseDTO convertToResponseDTO(Local local) {
        LocalResponseDTO dto = new LocalResponseDTO();
        dto.setId(local.getId());
        dto.setNome(local.getNome());

        if (local.getUsuariosProfissionais() != null) {
            List<Long> ids = local.getUsuariosProfissionais()
                    .stream()
                    .map(User::getId)
                    .collect(Collectors.toList());
            dto.setUsuariosProfissionais(ids);
        }

        return dto;
    }

}

