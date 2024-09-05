package br.com.diademaenforma.enformaplus.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// É onde definimos os atributos que serão retornados para o cliente, e não todos os atributos da entidade.
public class UsuarioDTO {
    private Long id;
    private String usuario;
    private String nome;
    private String email;
    private String papel;
}
