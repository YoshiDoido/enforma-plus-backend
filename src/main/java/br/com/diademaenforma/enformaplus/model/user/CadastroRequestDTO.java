package br.com.diademaenforma.enformaplus.model.user;

import lombok.Data;

@Data
public class CadastroRequestDTO {
    private String usuario;
    private String email;
    private String senha;
    private String papel;
    private String especialidade;
}
