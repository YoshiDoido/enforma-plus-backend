package br.com.diademaenforma.enformaplus.repository;

import br.com.diademaenforma.enformaplus.entity.Papel;
import br.com.diademaenforma.enformaplus.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /* Como esse é um repositório de dados para a superclasse Usuario,
     não é necessário adicionar métodos adicionais e
     nem sequer criar outros repositórios para os diferentes tipos de usuários.
     O Spring Data JPA já fornece métodos de CRUD para a superclasse Usuario.
     Por isso até o momento, só é preciso adicionar alguns personalizados de busca por email,
     busca por papeis,
     busca por nome, etc.
     Esses metodos são depois chamados na classe de serviço UsuarioService.
    */
    Usuario findByEmail(String email);

    // Busca uma lista usuários por papel
    // Ex: Busca todos os usuários com papel de ADMINISTRADOR
    List<Usuario> findAllByPapel(Papel papel);

    // Busca uma lista de usuários por nome
    // Ex: Busca todos os usuários com nome "João"
    List<Usuario> findAllByNome(String nome);
}
