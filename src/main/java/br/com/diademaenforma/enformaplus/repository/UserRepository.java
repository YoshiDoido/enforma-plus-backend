package br.com.diademaenforma.enformaplus.repository;

import br.com.diademaenforma.enformaplus.model.user.Especialidade;
import br.com.diademaenforma.enformaplus.model.user.Papel;
import br.com.diademaenforma.enformaplus.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByPapel(Papel papel);
    List<User> findByEspecialidade(Especialidade especialidade);
    List<User> findByEmailContainingIgnoreCase(String email);
    Optional<User> findByEmail(String email);

}

