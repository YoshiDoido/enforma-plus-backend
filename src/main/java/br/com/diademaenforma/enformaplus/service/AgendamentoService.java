package br.com.diademaenforma.enformaplus.service;

import br.com.diademaenforma.enformaplus.model.agendamento.*;
import br.com.diademaenforma.enformaplus.model.user.User;
import br.com.diademaenforma.enformaplus.repository.AgendamentoRepository;
import br.com.diademaenforma.enformaplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public AgendamentoResponseDTO createAgendamento(AgendamentoRequestDTO dto) {
        User cliente = userRepository.findById(dto.getUsuarioClienteId()).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        User profissional = userRepository.findById(dto.getProfissionalResponsavelId()).orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        if (cliente == null || profissional == null) return null;

        Agendamento agendamento = new Agendamento();
        agendamento.setData(dto.getData());
        agendamento.setHora(dto.getHora());
        agendamento.setDescricao(dto.getDescricao());
        agendamento.setTipo(Tipo.valueOf(dto.getTipo()));
        agendamento.setStatus(Status.valueOf(dto.getStatus()));
        agendamento.setUsuarioCliente(cliente);
        agendamento.setProfissionalResponsavel(profissional);

        agendamento = agendamentoRepository.save(agendamento);
        return toResponseDTO(agendamento);
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

    private AgendamentoResponseDTO toResponseDTO(Agendamento a) {
        UsuarioResumoDTO cliente = new UsuarioResumoDTO();
        cliente.setId(a.getUsuarioCliente().getId());
        cliente.setUser(a.getUsuarioCliente().getUsuario());

        ProfissionalResumoDTO profissional = new ProfissionalResumoDTO();
        profissional.setId(a.getProfissionalResponsavel().getId());
        profissional.setUser(a.getProfissionalResponsavel().getUsuario());
        profissional.setEspecialidade(a.getProfissionalResponsavel().getEspecialidade().name());

        AgendamentoResponseDTO dto = new AgendamentoResponseDTO();
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
