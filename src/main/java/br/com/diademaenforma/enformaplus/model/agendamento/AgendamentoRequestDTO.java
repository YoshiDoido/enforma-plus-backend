package br.com.diademaenforma.enformaplus.model.agendamento;

import lombok.Data;

@Data
public class AgendamentoRequestDTO {
    private String data;
    private String hora;
    private String descricao;
    private String tipo;
    private String status;
    private Long usuarioClienteId;
    private Long profissionalResponsavelId;
}
