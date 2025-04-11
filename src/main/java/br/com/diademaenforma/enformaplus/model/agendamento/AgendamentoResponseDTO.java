package br.com.diademaenforma.enformaplus.model.agendamento;

import lombok.Data;

@Data
public class AgendamentoResponseDTO {
    private String data;
    private String hora;
    private String descricao;
    private String tipo;
    private String status;
    private UsuarioResumoDTO usuarioCliente;
    private ProfissionalResumoDTO profissionalResponsavel;
}
