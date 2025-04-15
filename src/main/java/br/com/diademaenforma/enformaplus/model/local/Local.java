package br.com.diademaenforma.enformaplus.model.local;

import br.com.diademaenforma.enformaplus.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Nome do local
    private String nome;

    @OneToMany(mappedBy = "local")
    private List<User> usuariosProfissionais;
}
