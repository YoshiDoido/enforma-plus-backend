package br.com.diademaenforma.enformaplus.model.user;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String usuario;
    private String email;
    private String senha;
    private String papel;
    private String especialidade;
    private Long localId;
    private String localNome;
    private String dataCriacao;
    private String dataAtualizacao;

}
