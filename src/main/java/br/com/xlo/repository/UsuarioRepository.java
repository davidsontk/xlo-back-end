package br.com.xlo.repository;

import br.com.xlo.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Davidson
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Usuario findByUsername(String username);

    Usuario findByUsernameAndPassword(String username, String password);
    
    Usuario findByEmail(String email);
    
    List<Usuario> findAllByOrderByUsernameAsc();
}
