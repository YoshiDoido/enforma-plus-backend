package br.com.diademaenforma.enformaplus.service;

import br.com.diademaenforma.enformaplus.model.agendamento.Agendamento;
import br.com.diademaenforma.enformaplus.model.agendamento.AgendamentoRequestDTO;
import br.com.diademaenforma.enformaplus.model.agendamento.AgendamentoResponseDTO;
import br.com.diademaenforma.enformaplus.model.agendamento.Status;
import br.com.diademaenforma.enformaplus.model.agendamento.Tipo;
import br.com.diademaenforma.enformaplus.model.user.Especialidade;
import br.com.diademaenforma.enformaplus.model.user.User;
import br.com.diademaenforma.enformaplus.repository.AgendamentoRepository;
import br.com.diademaenforma.enformaplus.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AgendamentoServiceTest {

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ct01_agendamento_com_sucesso() {
        // Arrange
        AgendamentoRequestDTO request = new AgendamentoRequestDTO();
        request.setData("2025-05-10");
        request.setHora("14:00");
        request.setDescricao("Consulta psicológica");
        request.setTipo("PSICOLOGICO");
        request.setStatus("PENDENTE");
        request.setUsuarioClienteId(1L);
        request.setProfissionalResponsavelId(2L);

        User cliente = new User();
        cliente.setId(1L);
        cliente.setUsuario("João");

        User profissional = new User();
        profissional.setId(2L);
        profissional.setUsuario("Dra. Ana");
        profissional.setEspecialidade(Especialidade.PSICOLOGO);

        when(userRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(userRepository.findById(2L)).thenReturn(Optional.of(profissional));

        Agendamento agendamentoSalvo = new Agendamento();
        agendamentoSalvo.setId(10L);
        agendamentoSalvo.setData("2025-05-10");
        agendamentoSalvo.setHora("14:00");
        agendamentoSalvo.setDescricao("Consulta psicológica");
        agendamentoSalvo.setTipo(Tipo.CONSULTA);
        agendamentoSalvo.setStatus(Status.AGUARDANDO_CONFIRMACAO);
        agendamentoSalvo.setUsuarioCliente(cliente);
        agendamentoSalvo.setProfissionalResponsavel(profissional);

        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamentoSalvo);

        // Act
        AgendamentoResponseDTO response = agendamentoService.createAgendamento(request);

        // Assert
        assertEquals("2025-05-10", response.getData());
        assertEquals("14:00", response.getHora());
        assertEquals("Consulta psicológica", response.getDescricao());
        assertEquals("PSICOLOGICO", response.getTipo());
        assertEquals("PENDENTE", response.getStatus());
        assertEquals("João", response.getUsuarioCliente().getUser());
        assertEquals("Dra. Ana", response.getProfissionalResponsavel().getUsuario());
    }
}
