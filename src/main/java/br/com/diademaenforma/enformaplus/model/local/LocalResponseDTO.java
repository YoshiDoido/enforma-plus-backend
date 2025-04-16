package br.com.diademaenforma.enformaplus.model.local;

import br.com.diademaenforma.enformaplus.model.agendamento.ProfissionalResumoDTO;
import lombok.Data;

import java.util.List;

@Data
public class LocalResponseDTO {
    private Long id;
    private String nome;
    private List<ProfissionalResumoDTO> usuariosProfissionais;
}
