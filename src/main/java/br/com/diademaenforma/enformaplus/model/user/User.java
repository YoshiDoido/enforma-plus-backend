package br.com.diademaenforma.enformaplus.model.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "usuario")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String usuario;
    private String email;
    private String senha;
    private String telefone;
    private String papel;
    private String especialidade;
    @CreationTimestamp
    private String dataCriacao;
    @UpdateTimestamp
    private String dataAtualizacao;

}
