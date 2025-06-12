package br.com.diademaenforma.enformaplus.service;

import br.com.diademaenforma.enformaplus.model.acesso.AcessoProfissional;
import br.com.diademaenforma.enformaplus.model.acesso.AcessoResponseDTO;
import br.com.diademaenforma.enformaplus.model.agendamento.ProfissionalResumoComLocalDTO;
import br.com.diademaenforma.enformaplus.model.agendamento.UsuarioResumoDTO;
import br.com.diademaenforma.enformaplus.repository.AcessoProfissionalRepository;
import br.com.diademaenforma.enformaplus.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AcessoProfissionalService {

    private final AcessoProfissionalRepository acessoProfRepository;
    private final UserRepository userRepository;

    public AcessoProfissionalService(AcessoProfissionalRepository profissionalRepository,
                                     UserRepository userRepository) {
        this.acessoProfRepository = profissionalRepository;
        this.userRepository = userRepository;
    }

    public List<AcessoProfissional> buscarTodosAcessos() {
       return acessoProfRepository.findAll();
    }

    public AcessoProfissional registrarAcesso(Long usuarioId, Long profissionalId) {
        AcessoProfissional acesso = new AcessoProfissional();
        acesso.setUsuarioId(usuarioId);
        acesso.setProfissionalId(profissionalId);
        acesso.setDataHoraAcesso(LocalDateTime.now());

        return acessoProfRepository.save(acesso);
    }

    public List<AcessoResponseDTO> buscarTodosAcessosDetalhados() {
        return acessoProfRepository.findAll()
                .stream()
                .map(a -> {
                    var cliente = userRepository.findById(a.getUsuarioId())
                            .orElseThrow();
                    var prof    = userRepository.findById(a.getProfissionalId())
                            .orElseThrow();

                    UsuarioResumoDTO clienteDTO = new UsuarioResumoDTO();
                    clienteDTO.setId(cliente.getId());
                    clienteDTO.setUser(cliente.getUsuario());

                    ProfissionalResumoComLocalDTO profDTO = new ProfissionalResumoComLocalDTO();
                    profDTO.setProfissionalId(prof.getId());
                    profDTO.setUsuario(prof.getUsuario());
                    profDTO.setEspecialidade(
                            prof.getEspecialidade() != null
                                    ? prof.getEspecialidade().name()
                                    : "NÃO DEFINIDA"
                    );
                    if (prof.getLocal() != null) {
                        profDTO.setLocalNome(prof.getLocal().getNome());
                    }

                    return new AcessoResponseDTO(
                            a.getId(),
                            clienteDTO,
                            profDTO,
                            a.getDataHoraAcesso()
                    );
                })
                .toList();
    }
}
