package br.com.diademaenforma.enformaplus.model.local;

import lombok.Data;

@Data
public class LocalDTO {
    private Long id;
    private String nome;
    private String endereco;
    private String horarioFuncionamento;
    private String telefone;
}

