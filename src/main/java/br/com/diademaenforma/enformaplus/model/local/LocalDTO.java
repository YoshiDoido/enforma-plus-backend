package br.com.diademaenforma.enformaplus.model.local;

import lombok.Data;

import java.util.List;

@Data
public class LocalDTO {
    private String nome;
    private List<Long> usuariosProfissionais;
}
