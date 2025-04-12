package br.com.diademaenforma.enformaplus.model.user;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String senha;
}
