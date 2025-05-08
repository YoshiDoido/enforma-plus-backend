package br.com.diademaenforma.enformaplus.model.agendamento;

import lombok.Data;

@Data
public class ProfissionalResumoDTO {
    private Long profissionalId;
    private String usuario;
    private String especialidade;
}
