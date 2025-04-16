package br.com.diademaenforma.enformaplus.service;

import br.com.diademaenforma.enformaplus.model.agendamento.ProfissionalResumoDTO;
import br.com.diademaenforma.enformaplus.model.local.Local;
import br.com.diademaenforma.enformaplus.model.local.LocalDTO;
import br.com.diademaenforma.enformaplus.model.local.LocalResponseDTO;
import br.com.diademaenforma.enformaplus.repository.LocalRepository;
import br.com.diademaenforma.enformaplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                .map(this::convertToResponseDTO)
                .toList();
    }

    public LocalResponseDTO buscarPorId(Long id) {
        Local local = localRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Local não encontrado"));
        return convertToResponseDTO(local);
    }

    public LocalDTO atualizarLocal(Long id, LocalDTO dto) {
        Local local = localRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local não encontrado"));

        local.setNome(dto.getNome());
        local.setEndereco(dto.getEndereco());
        local.setHorarioFuncionamento(dto.getHorarioFuncionamento());
        local.setTelefone(dto.getTelefone());

        return convertToDTO(localRepository.save(local));
    }

    public void deletarLocal(Long id) {
        Local local = localRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local não encontrado"));

        // Desvincula os usuários, se o local já tiver pelo menos 1 usuário vinculado, irá acontecer um erro de integridade de dados
        local.getUsuariosProfissionais().forEach(user -> {
            user.setLocal(null);
            userRepository.save(user);
        });

        localRepository.deleteById(id);
    }


    private Local convertToEntity(LocalDTO dto) {
        Local local = new Local();
        local.setId(dto.getId());
        local.setNome(dto.getNome());
        local.setEndereco(dto.getEndereco());
        local.setHorarioFuncionamento(dto.getHorarioFuncionamento());
        local.setTelefone(dto.getTelefone());
        return local;
    }

    private LocalDTO convertToDTO(Local local) {
        LocalDTO dto = new LocalDTO();
        dto.setId(local.getId());
        dto.setNome(local.getNome());
        dto.setEndereco(local.getEndereco());
        dto.setHorarioFuncionamento(local.getHorarioFuncionamento());
        dto.setTelefone(local.getTelefone());
        return dto;
    }

    private LocalResponseDTO convertToResponseDTO(Local local) {
        LocalResponseDTO dto = new LocalResponseDTO();
        dto.setId(local.getId());
        dto.setNome(local.getNome());
        dto.setEndereco(local.getEndereco());
        dto.setHorarioFuncionamento(local.getHorarioFuncionamento());
        dto.setTelefone(local.getTelefone());

        if (local.getUsuariosProfissionais() != null) {
            List<ProfissionalResumoDTO> profissionais = local.getUsuariosProfissionais()
                    .stream()
                    .map(user -> {
                        ProfissionalResumoDTO p = new ProfissionalResumoDTO();
                        p.setProfissionalId(user.getId());
                        p.setUsuario(user.getUsuario());
                        p.setEspecialidade(user.getEspecialidade() != null ? user.getEspecialidade().toString() : null);
                        return p;
                    })
                    .collect(Collectors.toList());

            dto.setUsuariosProfissionais(profissionais);
        }

        return dto;
    }

}

