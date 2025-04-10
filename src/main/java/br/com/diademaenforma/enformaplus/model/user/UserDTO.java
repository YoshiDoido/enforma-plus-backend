package br.com.diademaenforma.enformaplus.model.user;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
public class UserDTO {

    private Long id;
    private String usuario;
    private String email;
    private String senha;
    private String papel;
    private String especialidade;
}
