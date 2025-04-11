package br.com.diademaenforma.enformaplus.model.agendamento;

import br.com.diademaenforma.enformaplus.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;
    private String hora;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id")
    private User usuarioCliente;
    @ManyToOne
    @JoinColumn(name = "profissional_responsavel_id")
    private User profissionalResponsavel;
    @CreationTimestamp
    private String dataCriacao;
    @UpdateTimestamp
    private String dataAtualizacao;
}
