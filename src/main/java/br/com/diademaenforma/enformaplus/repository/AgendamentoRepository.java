package br.com.diademaenforma.enformaplus.repository;

import br.com.diademaenforma.enformaplus.model.agendamento.Agendamento;
import br.com.diademaenforma.enformaplus.model.agendamento.Tipo;
import br.com.diademaenforma.enformaplus.model.user.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByProfissionalResponsavel_Especialidade(Especialidade especialidade);
    List<Agendamento> findByTipo(Tipo tipo);
    List<Agendamento> findByUsuarioCliente_Id(Long id);
    List<Agendamento> findByProfissionalResponsavel_Id(Long id);
    List<Agendamento> findByUsuarioCliente_UsuarioContainingIgnoreCase(String nome);
    List<Agendamento> findByProfissionalResponsavel_UsuarioContainingIgnoreCase(String nome);

}
