package br.com.diademaenforma.enformaplus.model.local;

import br.com.diademaenforma.enformaplus.model.agendamento.ProfissionalResumoDTO;
import lombok.Data;

import java.util.List;

@Data
public class LocalResponseDTO {
    private Long id;
    private String nome;
    private String endereco;
    private String horarioFuncionamento;
    private String telefone;
    private List<ProfissionalResumoDTO> usuariosProfissionais;
}