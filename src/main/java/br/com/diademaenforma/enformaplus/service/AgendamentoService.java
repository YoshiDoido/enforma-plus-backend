package br.com.diademaenforma.enformaplus.service;

import br.com.diademaenforma.enformaplus.exceptions.UsuarioNaoEncontradoException;
import br.com.diademaenforma.enformaplus.model.agendamento.*;
import br.com.diademaenforma.enformaplus.model.user.Especialidade;
import br.com.diademaenforma.enformaplus.model.user.User;
import br.com.diademaenforma.enformaplus.rabbitmq.AgendamentoProducer;
import br.com.diademaenforma.enformaplus.repository.AgendamentoRepository;
import br.com.diademaenforma.enformaplus.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    private final UserRepository userRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final AgendamentoProducer agendamentoProducer;
    private final AcessoProfissionalService acessoService;

    public AgendamentoService(UserRepository userRepository,  AgendamentoRepository agendamentoRepository,
                              AgendamentoProducer agendamentoProducer, AcessoProfissionalService acessoService) {
        this.userRepository = userRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.agendamentoProducer = agendamentoProducer;
        this.acessoService = acessoService;
    }

    public AgendamentoResponseDTO createAgendamento(AgendamentoRequestDTO dto) {
        List<Long> idsNaoEncontrados = new ArrayList<>();

        Optional<User> clienteOpt = userRepository.findById(dto.getUsuarioClienteId());
        Optional<User> profissionalOpt = userRepository.findById(dto.getProfissionalResponsavelId());

        if (clienteOpt.isEmpty()) idsNaoEncontrados.add(dto.getUsuarioClienteId());
        if (profissionalOpt.isEmpty()) idsNaoEncontrados.add(dto.getProfissionalResponsavelId());

        if (!idsNaoEncontrados.isEmpty()) {
            String mensagemErro = "Usuário(s) com ID(s) " + idsNaoEncontrados + " não existem.";
            throw new UsuarioNaoEncontradoException(mensagemErro);
        }

        User cliente = clienteOpt.get();
        User profissional = profissionalOpt.get();

        Agendamento agendamento = new Agendamento();
        agendamento.setData(dto.getData());
        agendamento.setHora(dto.getHora());
        agendamento.setDescricao(dto.getDescricao());
        agendamento.setTipo(Tipo.valueOf(dto.getTipo()));
        agendamento.setStatus(Status.valueOf(dto.getStatus()));
        agendamento.setUsuarioCliente(cliente);
        agendamento.setProfissionalResponsavel(profissional);

        agendamento = agendamentoRepository.save(agendamento);

        // Registra automaticamente o acesso após criar o agendamento
        acessoService.registrarAcesso(
                agendamento.getUsuarioCliente().getId(),
                agendamento.getProfissionalResponsavel().getId()
        );

        AgendamentoResponseDTO responseDTO = toResponseDTO(agendamento);
        agendamentoProducer.enviarMensagem(responseDTO);

        return responseDTO;
    }


    public List<AgendamentoResponseDTO> getAllAgendamentos() {
        return agendamentoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public AgendamentoResponseDTO getAgendamentoById(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        return toResponseDTO(agendamento);
    }

    public void deleteAgendamento(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        agendamentoRepository.delete(agendamento);
    }

    public AgendamentoResponseDTO updateStatus(Long id, String novoStatus) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        agendamento.setStatus(Status.valueOf(novoStatus));
        agendamento = agendamentoRepository.save(agendamento);

        return toResponseDTO(agendamento);
    }

    public List<AgendamentoResponseDTO> getAgendamentoByEspecialidade(Especialidade especialidade) {
        return agendamentoRepository.findByProfissionalResponsavel_Especialidade(especialidade)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<AgendamentoResponseDTO> getAgendamentosPorTipo(Tipo tipo) {
        return agendamentoRepository.findByTipo(tipo)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<AgendamentoResponseDTO> getAgendamentosPorUsuarioCliente(Long id) {
        return agendamentoRepository.findByUsuarioCliente_Id(id)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<AgendamentoResponseDTO> getAgendamentosPorProfissional(Long id) {
        return agendamentoRepository.findByProfissionalResponsavel_Id(id)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<AgendamentoResponseDTO> getAgendamentosPorNomeCliente(String nome) {
        return agendamentoRepository.findByUsuarioCliente_UsuarioContainingIgnoreCase(nome)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<AgendamentoResponseDTO> getAgendamentosPorNomeProfissional(String nome) {
        return agendamentoRepository.findByProfissionalResponsavel_UsuarioContainingIgnoreCase(nome)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private AgendamentoResponseDTO toResponseDTO(Agendamento a) {
        UsuarioResumoDTO cliente = new UsuarioResumoDTO();
        cliente.setId(a.getUsuarioCliente().getId());
        cliente.setUser(a.getUsuarioCliente().getUsuario());

        ProfissionalResumoComLocalDTO profissional = new ProfissionalResumoComLocalDTO();
        profissional.setProfissionalId(a.getProfissionalResponsavel().getId());
        profissional.setUsuario(a.getProfissionalResponsavel().getUsuario());

        // Buscar o profissional completo para garantir que especialidade e local estejam carregados
        User profissionalCompleto = userRepository.findById(a.getProfissionalResponsavel().getId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado ao converter agendamento"));

        // Garantir que a especialidade seja preenchida corretamente
        if (profissionalCompleto.getEspecialidade() != null) {
            profissional.setEspecialidade(profissionalCompleto.getEspecialidade().name());
        } else {
            profissional.setEspecialidade("NÃO DEFINIDA");
        }

        // Garantir que o local, se existir, seja preenchido corretamente
        if (profissionalCompleto.getLocal() != null) {
            profissional.setLocalNome(profissionalCompleto.getLocal().getNome());
        }

        AgendamentoResponseDTO dto = new AgendamentoResponseDTO();
        dto.setId(a.getId());
        dto.setData(a.getData());
        dto.setHora(a.getHora());
        dto.setDescricao(a.getDescricao());
        dto.setTipo(a.getTipo().name());
        dto.setStatus(a.getStatus().name());
        dto.setUsuarioCliente(cliente);
        dto.setProfissionalResponsavel(profissional);

        return dto;
    }



}
