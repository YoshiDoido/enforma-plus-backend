package br.com.diademaenforma.enformaplus.model.local;

import lombok.Data;

import java.util.List;

@Data
public class LocalResponseDTO {
    private Long id;
    private String nome;
    private List<Long> usuariosProfissionais;
}
