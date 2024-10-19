package br.fatec.bd4.repository;

import br.fatec.bd4.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u order by u.nome ASC")
    List<Usuario> findAllUsuarios();

    @Query("SELECT u FROM Usuario u where u.nome like %?1%")
    Optional<Usuario> findByNome(String nome);

}
