package br.com.diademaenforma.enformaplus.model.acesso;

import br.com.diademaenforma.enformaplus.model.agendamento.ProfissionalResumoComLocalDTO;
import br.com.diademaenforma.enformaplus.model.agendamento.UsuarioResumoDTO;

import java.time.LocalDateTime;

public record AcessoResponseDTO(
        Long id,
        UsuarioResumoDTO usuarioCliente,
        ProfissionalResumoComLocalDTO profissionalResponsavel,
        LocalDateTime dataHoraAcesso
) {}

